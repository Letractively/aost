// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/List.g 2009-09-15 23:40:40

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class ListParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "WS"
    };
    public static final int WS=6;
    public static final int INT=5;
    public static final int ID=4;
    public static final int EOF=-1;

    // delegates
    // delegators


        public ListParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ListParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ListParser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/List.g"; }


    public static class r_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "r"
    // /opt/workspace/test/parser/src/main/antlr3/List.g:3:1: r : ( ID | INT )+ ;
    public final ListParser.r_return r() throws RecognitionException {
        ListParser.r_return retval = new ListParser.r_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set1=null;

        Object set1_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/List.g:3:3: ( ( ID | INT )+ )
            // /opt/workspace/test/parser/src/main/antlr3/List.g:3:5: ( ID | INT )+
            {
            root_0 = (Object)adaptor.nil();

            // /opt/workspace/test/parser/src/main/antlr3/List.g:3:5: ( ID | INT )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=ID && LA1_0<=INT)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/List.g:
            	    {
            	    set1=(Token)input.LT(1);
            	    if ( (input.LA(1)>=ID && input.LA(1)<=INT) ) {
            	        input.consume();
            	        adaptor.addChild(root_0, (Object)adaptor.create(set1));
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
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
    // $ANTLR end "r"

    // Delegated rules


 

    public static final BitSet FOLLOW_set_in_r16 = new BitSet(new long[]{0x0000000000000032L});

}