package models;

import visitors.IAaiNodeVisitor;

public abstract class AaiNode {
	
	public abstract void accept(IAaiNodeVisitor visitor);

}
