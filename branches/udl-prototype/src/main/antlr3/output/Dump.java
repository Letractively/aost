// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/Dump.g 2009-09-15 16:37:00

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Dump extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "WS", "'+'"
    };
    public static final int INT=4;
    public static final int WS=5;
    public static final int EOF=-1;
    public static final int T__6=6;

    // delegates
    // delegators


        public Dump(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public Dump(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return Dump.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/Dump.g"; }



    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/Dump.g:6:1: expr : ( ^( '+' expr expr ) | INT );
    public final void expr() throws RecognitionException {
        CommonTree INT1=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/Dump.g:6:5: ( ^( '+' expr expr ) | INT )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==6) ) {
                alt1=1;
            }
            else if ( (LA1_0==INT) ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/Dump.g:6:9: ^( '+' expr expr )
                    {
                    match(input,6,FOLLOW_6_in_expr36); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr38);
                    expr();

                    state._fsp--;

                    System.out.print('+');
                    pushFollow(FOLLOW_expr_in_expr42);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/Dump.g:7:9: INT
                    {
                    INT1=(CommonTree)match(input,INT,FOLLOW_INT_in_expr54); 
                    System.out.print((INT1!=null?INT1.getText():null));

                    }
                    break;

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
    // $ANTLR end "expr"

    // Delegated rules


 

    public static final BitSet FOLLOW_6_in_expr36 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr38 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_expr_in_expr42 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INT_in_expr54 = new BitSet(new long[]{0x0000000000000002L});

}