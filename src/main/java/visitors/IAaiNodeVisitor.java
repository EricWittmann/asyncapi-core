package visitors;

import models.AaiDocument;
import models.AaiInfo;

public interface IAaiNodeVisitor {

	void visitDocument(AaiDocument node);

	void visitInfo(AaiInfo node);

}
