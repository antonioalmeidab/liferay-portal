/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checkstyle.util.DetailASTUtil;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class ExceptionMessageCheck extends BaseCheck {

	@Override
	public int[] getDefaultTokens() {
		return new int[] {TokenTypes.LITERAL_THROW};
	}

	@Override
	protected void doVisitToken(DetailAST detailAST) {
		DetailAST firstChildAST = detailAST.getFirstChild();

		firstChildAST = firstChildAST.getFirstChild();

		if (firstChildAST.getType() != TokenTypes.LITERAL_NEW) {
			return;
		}

		DetailAST elistAST = firstChildAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		for (DetailAST exprAST : exprASTList) {
			_checkMessage(_getLiteralStringValue(exprAST), exprAST.getLineNo());
		}
	}

	private void _checkMessage(String literalStringValue, int lineNo) {
		if (Validator.isNull(literalStringValue) ||
			literalStringValue.endsWith(StringPool.TRIPLE_PERIOD)) {

			return;
		}

		String[] parts = literalStringValue.split("\\S\\. [A-Z0-9]");

		if ((parts.length == 1) ^
			!literalStringValue.endsWith(StringPool.PERIOD)) {

			log(lineNo, _MSG_INCORRECT_MESSAGE);
		}
	}

	private String _getLiteralStringValue(DetailAST exprAST) {
		DetailAST firstChildAST = exprAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.STRING_LITERAL) {
			String s = firstChildAST.getText();

			return s.substring(1, s.length() - 1);
		}

		StringBundler sb = new StringBundler();

		if (firstChildAST.getType() == TokenTypes.PLUS) {
			DetailAST childAST = firstChildAST.getFirstChild();

			while (true) {
				if (childAST.getType() != TokenTypes.STRING_LITERAL) {
					return null;
				}

				String s = childAST.getText();

				sb.append(s.substring(1, s.length() - 1));

				childAST = childAST.getNextSibling();

				if (childAST == null) {
					return sb.toString();
				}
			}
		}

		if (firstChildAST.getType() != TokenTypes.METHOD_CALL) {
			return null;
		}

		String methodName = DetailASTUtil.getMethodName(firstChildAST);

		if (!methodName.equals("concat")) {
			return null;
		}

		DetailAST elistAST = firstChildAST.findFirstToken(TokenTypes.ELIST);

		List<DetailAST> exprASTList = DetailASTUtil.getAllChildTokens(
			elistAST, false, TokenTypes.EXPR);

		for (DetailAST curExprAST : exprASTList) {
			firstChildAST = curExprAST.getFirstChild();

			if (firstChildAST.getType() != TokenTypes.STRING_LITERAL) {
				return null;
			}

			String s = firstChildAST.getText();

			sb.append(s.substring(1, s.length() - 1));
		}

		return sb.toString();
	}

	private static final String _MSG_INCORRECT_MESSAGE = "message.incorrect";

}