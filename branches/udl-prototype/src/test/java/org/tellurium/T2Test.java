package org.tellurium;

import org.junit.Test;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Sep 15, 2009
 */
public class T2Test {

    @Test
    public void testParser() {
        String in = "abc #\n";
        try {
            ByteArrayInputStream bs = new ByteArrayInputStream(in.getBytes());
            ANTLRInputStream input = new ANTLRInputStream(bs);
            T2Lexer lexer = new T2Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            T2Parser parser = new T2Parser(tokens);
           parser.r();
        } catch (RecognitionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
