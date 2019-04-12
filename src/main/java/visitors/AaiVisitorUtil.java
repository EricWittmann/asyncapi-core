package visitors;

import models.AaiNode;

public class AaiVisitorUtil {
	
	public static void visitNode(AaiNode node, IAaiNodeVisitor visitor) {
		node.accept(visitor);
	}
	
	public static void visitTree(AaiNode node, IAaiNodeVisitor visitor) {
		AaiTraverser traverser = new AaiTraverser(visitor);
		traverser.traverse(node);
	}

}
