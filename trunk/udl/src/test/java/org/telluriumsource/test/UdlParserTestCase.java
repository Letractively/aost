package org.telluriumsource.test;
import org.junit.Test;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.telluriumsource.udl.MetaData;
import org.telluriumsource.udl.UdlLexer;
import org.telluriumsource.udl.UdlParser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class UdlParserTestCase {

    @Test
    public void testTableHeaderUid(){
 		CharStream stream =
			new ANTLRStringStream("{header: 3} as A");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
		System.out.println("ok");
    }

    @Test
    public void testTableBodyUid(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row : 2, column : 3} as Search");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
		System.out.println("ok");
    }
}
