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

package io.apicurio.asyncapi.core.io.writers;

import java.util.HashMap;
import java.util.Map;

import io.apicurio.asyncapi.core.compat.AaiJsonCompat;
import io.apicurio.asyncapi.core.io.AaiConstants;
import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.models.AaiNode;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;
import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiWriter implements IAaiNodeVisitor {

    private Object _result;
    private Map<Integer, Object> _modelIdToJS;
    
    /**
     * Constructor.
     */
    public AaiWriter() {
        this.reset();
    }
    
    /**
     * Resets the visitor.
     */
    private void reset() {
        this._modelIdToJS = new HashMap<>();
    }
    
    /**
     * Gets the result of the writing.
     */
    public Object getResult() {
        return this._result;
    }

    protected void updateIndex(AaiNode node, Object json) {
        this._modelIdToJS.put(node.modelId(), json);
        // Note: the first object created by the visitor is the result (we always traverse top-down).
        if (this._result == null) {
            this._result = json;
        }
    }

    protected Object lookup(int modelId) {
        Object rval = this._modelIdToJS.get(modelId);
        // If not found, return a throwaway object (this would happen when doing a partial
        // read of a subsection of a document).
        if (rval == null) {
            return AaiJsonCompat.objectNode();
        }
        return rval;
    }

    /**
     * Lookup a JS object using the model ID of the node's parent.
     * @param node
     * @return {any}
     */
    protected Object lookupParentJson(AaiNode node) {
        return this.lookup(node.parent().modelId());
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitDocument(io.apicurio.asyncapi.core.models.AaiDocument)
     */
    @Override
    public void visitDocument(AaiDocument node) {
        Object root = AaiJsonCompat.objectNode();
        AaiJsonCompat.setPropertyString(root, AaiConstants.PROP_ASYNCAPI, node.asyncapi);
        AaiJsonCompat.setPropertyString(root, AaiConstants.PROP_ID, node.id);
        
        this.updateIndex(node, root);
    }
    
    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitInfo(io.apicurio.asyncapi.core.models.AaiInfo)
     */
    @Override
    public void visitInfo(AaiInfo node) {
        Object parent = this.lookupParentJson(node);
        Object info = AaiJsonCompat.objectNode();
        AaiJsonCompat.setPropertyString(info, AaiConstants.PROP_TITLE, node.title);
        AaiJsonCompat.setPropertyString(info, AaiConstants.PROP_VERSION, node.version);
        AaiJsonCompat.setPropertyString(info, AaiConstants.PROP_DESCRIPTION, node.description);
        AaiJsonCompat.setProperty(parent, AaiConstants.PROP_INFO, info);

        this.updateIndex(node, info);
    }
    
    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitExtension(io.apicurio.asyncapi.core.models.AaiExtension)
     */
    @Override
    public void visitExtension(AaiExtension node) {
        Object parent = this.lookupParentJson(node);
        AaiJsonCompat.setProperty(parent, node.name, node.value);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitValidationProblem(io.apicurio.asyncapi.core.validation.AaiValidationProblem)
     */
    @Override
    public void visitValidationProblem(AaiValidationProblem problem) {
        // Validation problems are not written out, obviously.
    }

}
