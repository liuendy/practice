package com.fbb.codegen;

import com.fbb.codegen.core.CodeGen;

public class TestGen {

	public static void main(String[] args) {
		String result = CodeGen.CLASS_DECLARATION.replaceAll("\\{\\{tableName\\}\\}", "usermanager_log")
				.replaceAll("\\{\\{idName\\}\\}", "id").replaceAll("\\{\\{className\\}\\}", "UsermanagerLog");

		System.out.println(result);

	}

}
