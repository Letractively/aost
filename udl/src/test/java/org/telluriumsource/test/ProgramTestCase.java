package org.telluriumsource.test;

import org.junit.Test;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.telluriumsource.example.ProgramLexer;
import org.telluriumsource.example.ProgramParser;

import static org.junit.Assert.fail;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 17, 2010
 */
public class ProgramTestCase {

    @Test
    public void testParser(){
		CharStream stream =
			new ANTLRStringStream("program XLSample1 =\r\n" +
					"/*\r\n" +
					"	constant one : Integer := 1;\r\n" +
					"	constant two : Integer := 2 * 3;\r\n" +
					"	var x,         y, z : Integer := 42;\r\n" +
					"*/\r\n" +
					"\r\n" +
					"	procedure foo() =\r\n" +
					"		var x : Integer := 2;\r\n" +
					"	begin\r\n" +
					"	end foo.\r\n" +
					"	procedure fee(y : Integer) =\r\n" +
					"		var x : Integer := 2;\r\n" +
					"	begin\r\n" +
					"	end fee.\r\n" +
					"	function fie(y : Integer) : Integer =\r\n" +
					"		var x : Integer := 2;\r\n" +
					"	begin\r\n" +
					"		return y;\r\n" +
					"	end fie.\r\n" +
					"begin\r\n" +
					"end XLSample1.");
		ProgramLexer lexer = new ProgramLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ProgramParser parser = new ProgramParser(tokenStream);
        try{
		    parser.program();
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
		System.out.println("ok");
    }
}
