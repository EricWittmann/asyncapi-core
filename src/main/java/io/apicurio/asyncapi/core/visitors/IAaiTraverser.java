package io.apicurio.asyncapi.core.visitors;

import io.apicurio.asyncapi.core.models.AaiNode;

public interface IAaiTraverser {
	
	public void traverse(AaiNode node);

}
