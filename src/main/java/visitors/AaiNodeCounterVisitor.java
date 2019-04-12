package visitors;

import models.AaiDocument;
import models.AaiInfo;

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


}
