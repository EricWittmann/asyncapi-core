package io.apicurio.asyncapi.core.visitors;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;

public class AaiNodeCounterVisitor implements IAaiNodeVisitor {
	
	private int counter;
	
	public int getCounter() {
		return counter;
	}

	@Override
	public void visitDocument(AaiDocument node) {
		counter++;
	}

	@Override
	public void visitInfo(AaiInfo node) {
		counter++;
	}
	
	/**
	 * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitExtension(io.apicurio.asyncapi.core.models.AaiExtension)
	 */
	@Override
	public void visitExtension(AaiExtension node) {
	    counter++;
	}


}
