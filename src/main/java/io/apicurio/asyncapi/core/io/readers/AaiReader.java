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

import io.apicurio.asyncapi.core.compat.AaiJsonUtil;
import io.apicurio.asyncapi.core.io.AaiConstants;
import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiInfo;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiReader {

    public void readDocument(Object json, AaiDocument node) {
        String asyncapi = AaiJsonUtil.propertyString(json, AaiConstants.PROP_ASYNCAPI);
        String id = AaiJsonUtil.propertyString(json, AaiConstants.PROP_ID);
        Object info = AaiJsonUtil.property(json, AaiConstants.PROP_INFO);
        
        node.asyncapi = asyncapi;
        node.id = id;
        if (info != null) {
            node.info = node.createInfo();
            this.readInfo(info, node.info);
        }
    }

    public void readInfo(Object json, AaiInfo node) {
        String title = AaiJsonUtil.propertyString(json, AaiConstants.PROP_TITLE);
        String version = AaiJsonUtil.propertyString(json, AaiConstants.PROP_VERSION);
        String description = AaiJsonUtil.propertyString(json, AaiConstants.PROP_DESCRIPTION);
        
        node.title = title;
        node.version = version;
        node.description = description;
    }
    
}
