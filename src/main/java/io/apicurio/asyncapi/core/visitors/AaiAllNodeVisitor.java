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

package io.apicurio.asyncapi.core.visitors;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.models.AaiNode;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;

/**
 * Visitor used to visit all nodes in the same way.
 * @author eric.wittmann@gmail.com
 */
public class AaiAllNodeVisitor implements IAaiNodeVisitor {

    protected void visitNode(AaiNode node) {
        // Does nothing - subclasses should override this.  The class is not abstract because we 
        // want the compiler to yell at us when methods are added to IAaiNodeVisitor and not
        // implemented here.
    }
    
    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitDocument(io.apicurio.asyncapi.core.models.AaiDocument)
     */
    @Override
    public void visitDocument(AaiDocument node) {
        visitNode(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitInfo(io.apicurio.asyncapi.core.models.AaiInfo)
     */
    @Override
    public void visitInfo(AaiInfo node) {
        visitNode(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitExtension(io.apicurio.asyncapi.core.models.AaiExtension)
     */
    @Override
    public void visitExtension(AaiExtension node) {
        visitNode(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitValidationProblem(io.apicurio.asyncapi.core.validation.AaiValidationProblem)
     */
    @Override
    public void visitValidationProblem(AaiValidationProblem problem) {
        // Do nothing - problem is not a node.
    }

}
