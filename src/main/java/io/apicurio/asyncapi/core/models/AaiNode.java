package io.apicurio.asyncapi.core.models;

import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public abstract class AaiNode {
    
    private static int __modelIdCounter = 0;

    AaiDocument _ownerDocument;
    AaiNode _parent;
    int _modelId = __modelIdCounter++;

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

}
