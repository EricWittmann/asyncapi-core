/*
 * Copyright 2019 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.asyncapi.core.io;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.apicurio.asyncapi.core.io.readers.AaiReader;
import io.apicurio.asyncapi.core.io.writers.AaiWriter;
import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.visitors.AaiVisitorUtil;

/**
 * @author eric.wittmann@gmail.com
 */
public class IoTestRunner extends ParentRunner<FullIOTest> {

    private Class<?> testClass;
    private List<FullIOTest> children;
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor.
     * @param testClass
     * @throws InitializationError
     */
    public IoTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        this.testClass = testClass;
        this.children = loadTests();
    }

    private List<FullIOTest> loadTests() throws InitializationError {
        try {
            List<FullIOTest> allTests = new LinkedList<>();
            
            URL testsJsonUrl = Thread.currentThread().getContextClassLoader().getResource("fixtures/io/tests.json");
            String testsJsonSrc = IOUtils.toString(testsJsonUrl, "UTF-8");
            JsonNode tree = mapper.readTree(testsJsonSrc);
            ArrayNode tests = (ArrayNode) tree;
            tests.forEach( test -> {
                ObjectNode testNode = (ObjectNode) test;
                FullIOTest fit = new FullIOTest();
                fit.setName(testNode.get("name").asText());
                fit.setTest(testNode.get("test").asText());
                allTests.add(fit);
            });
            
            return allTests;
        } catch (IOException e) {
            throw new InitializationError(e);
        }
    }

    /**
     * @see org.junit.runners.ParentRunner#getChildren()
     */
    @Override
    protected List<FullIOTest> getChildren() {
        return children;
    }

    /**
     * @see org.junit.runners.ParentRunner#describeChild(java.lang.Object)
     */
    @Override
    protected Description describeChild(FullIOTest child) {
        return Description.createTestDescription(this.testClass, child.getName());
    }

    /**
     * @see org.junit.runners.ParentRunner#runChild(java.lang.Object, org.junit.runner.notification.RunNotifier)
     */
    @Override
    protected void runChild(FullIOTest child, RunNotifier notifier) {
        Description description = this.describeChild(child);
        Statement statement = new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String testCP = "fixtures/io/" + child.getTest();
                URL testUrl = Thread.currentThread().getContextClassLoader().getResource(testCP);
                Assert.assertNotNull(testUrl);

                // Read the test source
                String original = loadResource(testUrl);
                Assert.assertNotNull(original);
                // Parse into a Json object
                Object originalParsed = mapper.readTree(original);
                
                // Use the AaiReader to parse into a data model
                AaiDocument doc = new AaiDocument();
                AaiReader reader = new AaiReader();
                reader.readDocument(originalParsed, doc);
                
                // Use the AaiWriter to write the data model back to JSON
                AaiWriter writer = new AaiWriter();
                AaiVisitorUtil.visitTree(doc, writer);
                Object roundTripJs = writer.getResult();
                Assert.assertNotNull(roundTripJs);
                
                // Stringify the round trip object
                String roundTrip = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(roundTripJs);
                Assert.assertNotNull(roundTrip);
                assertJsonEquals(original, roundTrip);
            }
        };
        runLeaf(statement, description, notifier);
    }

    /**
     * Loads a resource as a string (reads the content at the URL).
     * @param testResource
     * @throws IOException
     */
    static String loadResource(URL testResource) throws IOException {
        return IOUtils.toString(testResource, "UTF-8");
    }

    /**
     * Compares two JSON strings.
     * @param expected
     * @param actual
     * @throws JSONException
     */
    static void assertJsonEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(expected, actual, true);
    }


}
