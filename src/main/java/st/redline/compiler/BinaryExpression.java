/* Redline Smalltalk, Copyright (c) James C. Ladd. All rights reserved. See LICENSE in the root of this distribution */
package st.redline.compiler;

import java.util.ArrayList;
import java.util.List;

public class BinaryExpression implements MessageExpression {

	private final List<BinarySelectorUnaryObjectDescription> binarySelectorUnaryObjectDescriptions;
	private KeywordExpression keywordExpression;

	BinaryExpression() {
		binarySelectorUnaryObjectDescriptions = new ArrayList<BinarySelectorUnaryObjectDescription>();
	}

	void add(KeywordExpression keywordExpression) {
		this.keywordExpression = keywordExpression;
	}

	KeywordExpression keywordExpression() {
		return keywordExpression;
	}

	void add(BinarySelector binarySelector, UnaryObjectDescription unaryObjectDescription) {
		binarySelectorUnaryObjectDescriptions.add(new BinarySelectorUnaryObjectDescription(binarySelector, unaryObjectDescription));
	}

	List<BinarySelectorUnaryObjectDescription> binarySelectorUnaryObjectDescriptions() {
		return binarySelectorUnaryObjectDescriptions;
	}
}
