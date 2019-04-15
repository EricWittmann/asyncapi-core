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

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiJsonUtil {
    
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

}
