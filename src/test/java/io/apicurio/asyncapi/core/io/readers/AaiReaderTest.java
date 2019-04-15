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

package io.apicurio.asyncapi.core.io.readers;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.apicurio.asyncapi.core.models.AaiDocument;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiReaderTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Test method for {@link io.apicurio.asyncapi.core.io.readers.AaiReader#readDocument(java.lang.Object, io.apicurio.asyncapi.core.models.AaiDocument)}.
     */
    @Test
    public void testReadDocument() throws Exception {
        String source = "{\r\n" + 
                "    \"asyncapi\": \"2.0.0\",\r\n" + 
                "    \"id\": \"12345\",\r\n" + 
                "    \"info\": {\r\n" + 
                "        \"title\": \"API Title\",\r\n" + 
                "        \"version\": \"1.0.0\",\r\n" + 
                "        \"description\": \"This is the API description.\"\r\n" + 
                "    }\r\n" + 
                "}";
        
        AaiReader reader = new AaiReader();
        AaiDocument document = new AaiDocument();
        JsonNode json = mapper.readTree(source);
        reader.readDocument(json, document);
        
        Assert.assertEquals("2.0.0", document.asyncapi);
        Assert.assertEquals("12345", document.id);
        Assert.assertNotNull(document.info);
        Assert.assertEquals("API Title", document.info.title);
        Assert.assertEquals("1.0.0", document.info.version);
        Assert.assertEquals("This is the API description.", document.info.description);
    }

}
