package org.telluriumsource.test;
import org.junit.Test;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.telluriumsource.udl.MetaData;
import org.telluriumsource.udl.TableBodyMetaData;
import org.telluriumsource.udl.UdlLexer;
import org.telluriumsource.udl.UdlParser;
import org.telluriumsource.udl.code.IndexType;

import static org.junit.Assert.*;

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
    public void testTableBodyValUid(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row : 2, column : 3} as Search");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData)data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("2", tbmd.getRow().getValue());
            assertEquals(IndexType.VAL, tbmd.getRow().getType());
            assertEquals("3", tbmd.getColumn().getValue());
            assertEquals(IndexType.VAL, tbmd.getColumn().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testTableBodyRefUid(){
		CharStream stream =
			new ANTLRStringStream("{tbody : 1, row = good, column = bad} as Search");
		UdlLexer lexer = new UdlLexer(stream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		UdlParser parser = new UdlParser(tokenStream);
        try{
		    MetaData data = parser.uid();
            assertNotNull(data);
            assertEquals("Search", data.getId());
            assertTrue(data instanceof TableBodyMetaData);
            TableBodyMetaData tbmd = (TableBodyMetaData)data;
            assertEquals("1", tbmd.getTbody().getValue());
            assertEquals(IndexType.VAL, tbmd.getTbody().getType());
            assertEquals("good", tbmd.getRow().getValue());
            assertEquals(IndexType.REF, tbmd.getRow().getType());
            assertEquals("bad", tbmd.getColumn().getValue());
            assertEquals(IndexType.REF, tbmd.getColumn().getType());
        }catch(RecognitionException e){
            fail(e.getMessage());
        }
    }
}
