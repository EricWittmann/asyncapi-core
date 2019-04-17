package io.apicurio.asyncapi.core.visitors;

import io.apicurio.asyncapi.core.models.AaiDocument;
import io.apicurio.asyncapi.core.models.AaiExtension;
import io.apicurio.asyncapi.core.models.AaiInfo;
import io.apicurio.asyncapi.core.validation.AaiValidationProblem;

public interface IAaiNodeVisitor {

	void visitDocument(AaiDocument node);
	void visitInfo(AaiInfo node);
    void visitExtension(AaiExtension node);
    void visitValidationProblem(AaiValidationProblem problem);
}
