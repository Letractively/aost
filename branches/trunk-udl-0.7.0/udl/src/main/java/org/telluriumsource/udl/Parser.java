package org.telluriumsource.udl;

import org.antlr.runtime.*;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 19, 2010
 */
public class Parser {
    
    public static MetaData parse(String uid) throws RecognitionException {
        if (uid == null || uid.trim().length() == 0)
            return null;

        CharStream stream = new ANTLRStringStream(uid);
        UdlLexer lexer = new UdlLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        UdlParser parser = new UdlParser(tokenStream);
        return parser.uid();
    }
}
