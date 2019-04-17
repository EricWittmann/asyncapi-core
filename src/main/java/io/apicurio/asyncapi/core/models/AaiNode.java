package io.apicurio.asyncapi.core.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.apicurio.asyncapi.core.validation.AaiValidationProblem;
import io.apicurio.asyncapi.core.validation.AaiValidationProblemSeverity;
import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public abstract class AaiNode implements IAaiVisitable {
    
    private static int __modelIdCounter = 0;

    protected AaiDocument _ownerDocument;
    protected AaiNode _parent;
    protected int _modelId = __modelIdCounter++;
    protected Map<String, Object> _attributes;
    protected Map<String, AaiValidationProblem> _validationProblems = new HashMap<>();

    /**
     * Gets the owner document.
     */
    public AaiDocument ownerDocument() {
        return this._ownerDocument;
    }

    /**
     * Gets the parent.
     */
    public AaiNode parent() {
        return this._parent;
    }

    /**
     * Gets the model's unique ID.
     */
    public int modelId() {
        return this._modelId;
    }

    /**
     * @see io.apicurio.asyncapi.core.models.IAaiVisitable#accept(io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor)
     */
	public abstract void accept(IAaiNodeVisitor visitor);
	
	public Object getAttribute(String attributeName) {
	    if (this._attributes != null) {
	        return this._attributes.get(attributeName);
	    } else {
	        return null;
	    }
	}
	
	public void setAttribute(String attributeName, Object attributeValue) {
	    if (this._attributes == null) {
	        this._attributes = new HashMap<>();
	    }
	    this._attributes.put(attributeName, attributeValue);
	}
	
	public Collection<String> getAttributeNames() {
	    if (this._attributes != null) {
	        return this._attributes.keySet();
	    } else {
	        return Collections.emptyList();
	    }
	}
	
	public void clearAttributes() {
	    if (this._attributes != null) {
	        this._attributes.clear();
	    }
	}
	
	public List<String> getValidationProblemCodes() {
	    return new ArrayList<>(this._validationProblems.keySet());
	}
	
	public List<AaiValidationProblem> getValidationProblems() {
	    return new ArrayList<>(this._validationProblems.values());
	}
	
	public List<AaiValidationProblem> getValidationProblemsFor(String propertyName) {
	    List<AaiValidationProblem> rval = new ArrayList<>();
	    for (AaiValidationProblem problem : this._validationProblems.values()) {
            if (problem.property.equals(propertyName)) {
                rval.add(problem);
            }
        }
	    return rval;
	}
	
    public AaiValidationProblem addValidationProblem(String errorCode, Object nodePath, String property,
            String message, AaiValidationProblemSeverity severity) {
        AaiValidationProblem problem = new AaiValidationProblem(errorCode, nodePath, property, message, severity);
        this._validationProblems.put(errorCode, problem);
        return problem;
    }
	
	public void clearValidationProblems() {
	    this._validationProblems.clear();
	}

}
