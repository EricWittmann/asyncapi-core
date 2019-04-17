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

package io.apicurio.asyncapi.core.validation;

import java.util.ArrayList;
import java.util.List;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiNode;
import io.apicurio.asyncapi.core.visitors.AaiCompositeVisitor;

/**
 * @author eric.wittmann@gmail.com
 */
public class AaiValidationVisitor extends AaiCompositeVisitor implements IAaiValidationProblemReporter {
    
    private List<AaiValidationProblem> problems = new ArrayList<>();
    private IAaiValidationSeverityRegistry severityRegistry;
    
    /**
     * Constructor.
     * @param document
     */
    public AaiValidationVisitor(AaiDocument document) {
        super(new ArrayList<>());
        AaiValidationRuleSet ruleSet = AaiValidationRuleSet.instance;
        List<AaiValidationRule> rulesFor = ruleSet.getRulesFor(document);
        for (AaiValidationRule rule : rulesFor) {
            rule.setReporter(this);
        }
        this.addVisitors(rulesFor);
    }
    
    /**
     * Sets the severity registry.
     * @param severityRegistry
     */
    public void setSeverityRegistry(IAaiValidationSeverityRegistry severityRegistry) {
        this.severityRegistry = severityRegistry;
    }
    
    /**
     * Accessor for the problems.
     */
    public List<AaiValidationProblem> getValidationProblems() {
        return this.problems;
    }

    /**
     * @see io.apicurio.asyncapi.core.validation.IAaiValidationProblemReporter#report(io.apicurio.asyncapi.core.validation.AaiValidationRuleMetaData, io.apicurio.asyncapi.core.models.AaiNode, java.lang.String, java.lang.String)
     */
    @Override
    public void report(AaiValidationRuleMetaData ruleInfo, AaiNode node, String property, String message) {
        AaiValidationProblemSeverity severity = this.severityRegistry.lookupSeverity(ruleInfo);
        if (severity == AaiValidationProblemSeverity.ignore) {
            return;
        }
        
        // TODO create the node path here so it can be reported
        Object path = new Object();
        AaiValidationProblem problem = node.addValidationProblem(ruleInfo.code, path, property, message, severity);
        
        // Include in the list of problems tracked by the validator.
        this.problems.add(problem);
    }

}
