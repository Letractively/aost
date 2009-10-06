// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/P.g 2009-09-15 16:34:07

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class PParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "WS", "'+'"
    };
    public static final int WS=5;
    public static final int INT=4;
    public static final int EOF=-1;
    public static final int T__6=6;

    // delegates
    // delegators


        public PParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public PParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return PParser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/P.g"; }


    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/P.g:5:1: expr : INT ( '+' INT )* ;
    public final PParser.expr_return expr() throws RecognitionException {
        PParser.expr_return retval = new PParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token INT1=null;
        Token char_literal2=null;
        Token INT3=null;

        Object INT1_tree=null;
        Object char_literal2_tree=null;
        Object INT3_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/P.g:5:5: ( INT ( '+' INT )* )
            // /opt/workspace/test/parser/src/main/antlr3/P.g:5:9: INT ( '+' INT )*
            {
            root_0 = (Object)adaptor.nil();

            INT1=(Token)match(input,INT,FOLLOW_INT_in_expr23); 
            INT1_tree = (Object)adaptor.create(INT1);
            adaptor.addChild(root_0, INT1_tree);

            // /opt/workspace/test/parser/src/main/antlr3/P.g:5:13: ( '+' INT )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==6) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/P.g:5:14: '+' INT
            	    {
            	    char_literal2=(Token)match(input,6,FOLLOW_6_in_expr26); 
            	    char_literal2_tree = (Object)adaptor.create(char_literal2);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal2_tree, root_0);

            	    INT3=(Token)match(input,INT,FOLLOW_INT_in_expr29); 
            	    INT3_tree = (Object)adaptor.create(INT3);
            	    adaptor.addChild(root_0, INT3_tree);


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    // Delegated rules


 

    public static final BitSet FOLLOW_INT_in_expr23 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_6_in_expr26 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_expr29 = new BitSet(new long[]{0x0000000000000042L});

}