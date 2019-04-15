package io.apicurio.asyncapi.core.models;

import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public class AaiDocument extends AaiNode {
	
	public String asyncapi;
	public String id;
	public AaiInfo info;
	
	/**
     * Constructor.
     */
    public AaiDocument() {
        this._ownerDocument = this;
        this._parent = null;
    }

	@Override
	public void accept(IAaiNodeVisitor visitor) {
		visitor.visitDocument(this);
	}

    public AaiInfo createInfo() {
        AaiInfo info = new AaiInfo();
        info._parent = this;
        info._ownerDocument = this;
        return info;
    }

}
