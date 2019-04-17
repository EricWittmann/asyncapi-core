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

import java.util.ArrayList;
import java.util.List;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.models.IAaiVisitable;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;

/**
 * A simple visitor that delegates to a list of *other* visitors.  Basically converts
 * a list of visitors to a single visitor.
 * @author eric.wittmann@gmail.com
 */
public class AaiCompositeVisitor implements IAaiNodeVisitor {
    
    private List<IAaiNodeVisitor> visitors = new ArrayList<>();

    /**
     * Constructor.
     * @param visitors
     */
    public AaiCompositeVisitor(List<IAaiNodeVisitor> visitors) {
        this.visitors = visitors;
    }
    
    /**
     * Adds a visitor.
     * @param visitor
     */
    public void addVisitor(IAaiNodeVisitor visitor) {
        this.visitors.add(visitor);
    }
    
    /**
     * Adds multiple visitors.
     * @param visitors
     */
    public void addVisitors(List<? extends IAaiNodeVisitor> visitors) {
        this.visitors.addAll(visitors);
    }
    
    /**
     * Make the node accept all of the visitors.
     * @param node
     */
    protected void acceptAll(IAaiVisitable node) {
        for (IAaiNodeVisitor visitor : this.visitors) {
            node.accept(visitor);
        }
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitDocument(io.apicurio.asyncapi.core.models.AaiDocument)
     */
    @Override
    public void visitDocument(AaiDocument node) {
        this.acceptAll(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitInfo(io.apicurio.asyncapi.core.models.AaiInfo)
     */
    @Override
    public void visitInfo(AaiInfo node) {
        this.acceptAll(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitExtension(io.apicurio.asyncapi.core.models.AaiExtension)
     */
    @Override
    public void visitExtension(AaiExtension node) {
        this.acceptAll(node);
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitValidationProblem(io.apicurio.asyncapi.core.validation.AaiValidationProblem)
     */
    @Override
    public void visitValidationProblem(AaiValidationProblem problem) {
        this.acceptAll(problem);
    }

}
