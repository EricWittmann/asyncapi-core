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

package io.apicurio.asyncapi.core.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eric.wittmann@gmail.com
 */
public abstract class AaiExtensibleNode extends AaiNode {

    private Map<String, AaiExtension> _extensions;
    
    public AaiExtension createExtension() {
        AaiExtension extension = new AaiExtension();
        extension._parent = this;
        extension._ownerDocument = this.ownerDocument();
        return extension;
    }
    
    public void addExtension(String name, AaiExtension extension) {
        if (this._extensions == null) {
            this._extensions = new HashMap<>();
        }
        this._extensions.put(name, extension);
    }
    
    public Collection<AaiExtension> getExtensions() {
        if (this._extensions != null) {
            return this._extensions.values();
        } else {
            return null;
        }
    }
    
    public AaiExtension getExtension(String name) {
        AaiExtension rval = null;
        if (this._extensions != null) {
            rval = this._extensions.get(name);
        }
        return rval;
    }
    
    public void removeExtension(String name) {
        if (this._extensions != null) {
            this._extensions.remove(name);
        }
    }
    
    public void clearExtensions() {
        if (this._extensions != null) {
            this._extensions.clear();
        }
    }
    
}
