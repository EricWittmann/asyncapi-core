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

package io.apicurio.asyncapi.core.compat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiJsonUtil {

    private static final JsonNodeFactory factory = JsonNodeFactory.instance;

    public static ObjectNode objectNode() {
        return factory.objectNode();
    }
    public static ArrayNode arrayNode() {
        return factory.arrayNode();
    }
    
    /*
     * Getters
     */
    
    public static Object property(Object json, String propertyName) {
        JsonNode node = (JsonNode) json;
        return node.get(propertyName);
    }
    
    public static String propertyString(Object json, String propertyName) {
        JsonNode propertyNode = (JsonNode) AaiJsonUtil.property(json, propertyName);
        if (propertyNode == null) {
            return null;
        } else {
            return propertyNode.asText();
        }
    }
    
    /*
     * Setters
     */
    
    public static void setProperty(Object json, String propertyName, Object propertyValue) {
        ObjectNode node = (ObjectNode) json;
        JsonNode value = (JsonNode) propertyValue;
        node.set(propertyName, value);
    }
    
    public static void setPropertyString(Object json, String propertyName, String propertyValue) {
        if (propertyValue != null) {
            ObjectNode node = (ObjectNode) json;
            TextNode textNode = factory.textNode(propertyValue);
            node.set(propertyName, textNode);
        }
    }

}
