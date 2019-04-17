package io.apicurio.asyncapi.core.visitors;

import java.util.Collection;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtensibleNode;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.models.AaiNode;
import io.apicurio.asyncapi.core.models.IAaiVisitable;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;

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
     * Traverse the items of the given array.
     * @param items
     */
    protected void traverseCollection(Collection<? extends IAaiVisitable> items) {
        if (items != null) {
            for (IAaiVisitable node : items) {
                this.traverseIfNotNull(node);
            }
        }
    }

    /**
     * Traverse the extension nodes, if any are found.
     * @param node
     */
    protected void traverseExtensions(AaiExtensibleNode node) {
        this.traverseCollection(node.getExtensions());
    }

    /**
     * Traverse the validation problems, if any are found.
     * @param node
     */
    protected void traverseValidationProblems(AaiNode node) {
        this.traverseCollection(node.getValidationProblems());
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
    protected void traverseIfNotNull(IAaiVisitable node) {
        if (node != null) {
            node.accept(this);
        }
    }

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitDocument(io.apicurio.asyncapi.core.models.AaiDocument)
     */
	@Override
	public void visitDocument(AaiDocument node) {
		node.accept(this.visitor);
		this.traverseIfNotNull(node.info);
        this.traverseExtensions(node);
        this.traverseValidationProblems(node);
	}

	/**
	 * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitInfo(io.apicurio.asyncapi.core.models.AaiInfo)
	 */
	@Override
	public void visitInfo(AaiInfo node) {
		node.accept(this.visitor);
        this.traverseExtensions(node);
        this.traverseValidationProblems(node);
	}
	
	/**
	 * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitExtension(io.apicurio.asyncapi.core.models.AaiExtension)
	 */
	@Override
	public void visitExtension(AaiExtension node) {
	    node.accept(this.visitor);
        this.traverseValidationProblems(node);
	}

    /**
     * @see io.apicurio.asyncapi.core.visitors.IAaiNodeVisitor#visitValidationProblem(io.apicurio.asyncapi.core.validation.AaiValidationProblem)
     */
    @Override
    public void visitValidationProblem(AaiValidationProblem problem) {
        problem.accept(this.visitor);
    }

}
