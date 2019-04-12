package io.apicurio.asyncapi.core.visitors;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.models.AaiNode;

public class AaiTraverser implements IAaiTraverser, IAaiNodeVisitor {
	
	private IAaiNodeVisitor visitor;
	
	/**
	 * C'tor.
	 * @param visitor
	 */
	public AaiTraverser(IAaiNodeVisitor visitor) {
		this.visitor = visitor;
	}

    /**
     * Called to traverse the data model starting at the given node and traversing
     * down until this node and all child nodes have been visited.
     * @param node
     */
    public void traverse(AaiNode node) {
        node.accept(this);
    }

    /**
     * Traverse into the given node, unless it's null.
     * @param node
     */
    protected void traverseIfNotNull(AaiNode node) {
        if (node != null) {
            node.accept(this);
        }
    }

	@Override
	public void visitDocument(AaiDocument node) {
		node.accept(this.visitor);
		this.traverseIfNotNull(node.info);
	}

	@Override
	public void visitInfo(AaiInfo node) {
		node.accept(this.visitor);
	}

}
