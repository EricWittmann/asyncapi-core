package io.apicurio.asyncapi.core;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.visitors.AaiNodeCounterVisitor;
import io.apicurio.asyncapi.core.visitors.AaiVisitorUtil;

public class Tester {
	
	public static void main(String[] args) {
		AaiDocument doc = new AaiDocument();
		doc.asyncapi = "2.0.0";
		doc.id = "1";
		doc.info = new AaiInfo();
		doc.info.title = "Foo";
		doc.info.description = "The API description";
		doc.info.version = "1.0";
		
		AaiNodeCounterVisitor visitor = new AaiNodeCounterVisitor();
		AaiVisitorUtil.visitNode(doc, visitor);
		System.out.println("Count: " + visitor.getCounter());
		
		visitor = new AaiNodeCounterVisitor();
		AaiVisitorUtil.visitTree(doc, visitor);
		System.out.println("Count: " + visitor.getCounter());
	}

}
