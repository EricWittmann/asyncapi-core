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

package io.apicurio.asyncapi.core;

import java.util.List;

import io.apicurio.asyncapi.core.models.AaiNode;
import io.apicurio.asyncapi.core.validation.AaiDefaultSeverityRegistry;
import io.apicurio.asyncapi.core.validation.AaiResetValidationProblemsVisitor;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;
import io.apicurio.asyncapi.core.validation.AaiValidationVisitor;
import io.apicurio.asyncapi.core.validation.IAaiValidationSeverityRegistry;
import io.apicurio.asyncapi.core.visitors.AaiTraverser;
import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiLibrary {

    public static void visitNode(AaiNode node, IAaiNodeVisitor visitor) {
        node.accept(visitor);
    }
    
    public static void visitTree(AaiNode node, IAaiNodeVisitor visitor) {
        AaiTraverser traverser = new AaiTraverser(visitor);
        traverser.traverse(node);
    }

    public static List<AaiValidationProblem> validate(AaiNode node, IAaiValidationSeverityRegistry severityRegistry) {
        if (severityRegistry == null) {
            severityRegistry = new AaiDefaultSeverityRegistry();
        }
        
        // Clear/reset any problems that may have been found before.
        AaiResetValidationProblemsVisitor resetter = new AaiResetValidationProblemsVisitor();
        visitTree(node, resetter);
        
        // Validate the data model.
        AaiValidationVisitor validator = new AaiValidationVisitor(node.ownerDocument());
        validator.setSeverityRegistry(severityRegistry);
        visitTree(node, validator);
        
        return validator.getValidationProblems();
    }

}
