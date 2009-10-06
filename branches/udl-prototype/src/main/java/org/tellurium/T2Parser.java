// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/T2.g 2009-09-15 13:51:02
package org.tellurium;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class T2Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "WS", "'#'"
    };
    public static final int WS=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__6=6;

    // delegates
    // delegators


        public T2Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public T2Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return T2Parser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/T2.g"; }


    String s;



    // $ANTLR start "r"
    // /opt/workspace/test/parser/src/main/antlr3/T2.g:8:1: r : ID '#' ;
    public final void r() throws RecognitionException {
        Token ID1=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T2.g:8:3: ( ID '#' )
            // /opt/workspace/test/parser/src/main/antlr3/T2.g:8:5: ID '#'
            {
            ID1=(Token)match(input,ID,FOLLOW_ID_in_r27); 
            match(input,6,FOLLOW_6_in_r29); 
            s = (ID1!=null?ID1.getText():null); System.out.println("found "+s);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "r"

    // Delegated rules


 

    public static final BitSet FOLLOW_ID_in_r27 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_6_in_r29 = new BitSet(new long[]{0x0000000000000002L});

}