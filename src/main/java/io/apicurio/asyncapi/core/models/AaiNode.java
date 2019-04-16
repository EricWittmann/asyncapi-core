package io.apicurio.asyncapi.core.models;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public abstract class AaiNode {
    
    private static int __modelIdCounter = 0;

    AaiDocument _ownerDocument;
    AaiNode _parent;
    int _modelId = __modelIdCounter++;
    Map<String, Object> _attributes;
    

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

}
