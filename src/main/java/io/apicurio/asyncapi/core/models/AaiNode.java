package io.apicurio.asyncapi.core.models;

import io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor;

public abstract class AaiNode {
	
	public abstract void accept(IAaiNodeVisitor visitor);

}
