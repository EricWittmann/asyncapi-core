package io.apicurio.asyncapi.core.models;

import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public class AaiDocument extends AaiNode {
	
	public String asyncapi;
	public String id;
	public AaiInfo info;

	@Override
	public void accept(IAaiNodeVisitor visitor) {
		visitor.visitDocument(this);
	}

}