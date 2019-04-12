package models;

import visitors.IAaiNodeVisitor;

public class AaiInfo extends AaiNode {
	
	public String title;
	public String version;
	public String description;

	@Override
	public void accept(IAaiNodeVisitor visitor) {
		visitor.visitInfo(this);
	}

}
