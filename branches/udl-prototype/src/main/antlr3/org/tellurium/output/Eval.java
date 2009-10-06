// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g 2009-09-15 08:57:06

import java.util.HashMap;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Eval extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEWLINE", "ID", "INT", "WS", "'='", "'+'", "'-'", "'*'", "'('", "')'"
    };
    public static final int WS=7;
    public static final int NEWLINE=4;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int INT=6;
    public static final int ID=5;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;

    // delegates
    // delegators


        public Eval(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public Eval(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return Eval.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g"; }


    /** Map variable name to Integer object holding value */
    HashMap memory = new HashMap();



    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:20:1: prog : ( stat )+ ;
    public final void prog() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:20:5: ( ( stat )+ )
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:20:9: ( stat )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:20:9: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=ID && LA1_0<=INT)||(LA1_0>=8 && LA1_0<=11)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:20:9: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog51);
            	    stat();

            	    state._fsp--;


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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "prog"


    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:22:1: stat : ( expr | ^( '=' ID expr ) );
    public final void stat() throws RecognitionException {
        CommonTree ID2=null;
        int expr1 = 0;

        int expr3 = 0;


        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:22:5: ( expr | ^( '=' ID expr ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=ID && LA2_0<=INT)||(LA2_0>=9 && LA2_0<=11)) ) {
                alt2=1;
            }
            else if ( (LA2_0==8) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:22:9: expr
                    {
                    pushFollow(FOLLOW_expr_in_stat62);
                    expr1=expr();

                    state._fsp--;

                    System.out.println(expr1);

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:24:9: ^( '=' ID expr )
                    {
                    match(input,8,FOLLOW_8_in_stat83); 

                    match(input, Token.DOWN, null); 
                    ID2=(CommonTree)match(input,ID,FOLLOW_ID_in_stat85); 
                    pushFollow(FOLLOW_expr_in_stat87);
                    expr3=expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    memory.put((ID2!=null?ID2.getText():null), new Integer(expr3));

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
    // $ANTLR end "stat"


    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:30:1: expr returns [int value] : ( ^( '+' a= expr b= expr ) | ^( '-' a= expr b= expr ) | ^( '*' a= expr b= expr ) | ID | INT );
    public final int expr() throws RecognitionException {
        int value = 0;

        CommonTree ID4=null;
        CommonTree INT5=null;
        int a = 0;

        int b = 0;


        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:31:5: ( ^( '+' a= expr b= expr ) | ^( '-' a= expr b= expr ) | ^( '*' a= expr b= expr ) | ID | INT )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 9:
                {
                alt3=1;
                }
                break;
            case 10:
                {
                alt3=2;
                }
                break;
            case 11:
                {
                alt3=3;
                }
                break;
            case ID:
                {
                alt3=4;
                }
                break;
            case INT:
                {
                alt3=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:31:9: ^( '+' a= expr b= expr )
                    {
                    match(input,9,FOLLOW_9_in_expr124); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr128);
                    a=expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr132);
                    b=expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    value = a+b;

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:32:9: ^( '-' a= expr b= expr )
                    {
                    match(input,10,FOLLOW_10_in_expr146); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr150);
                    a=expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr154);
                    b=expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    value = a-b;

                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:33:9: ^( '*' a= expr b= expr )
                    {
                    match(input,11,FOLLOW_11_in_expr171); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr175);
                    a=expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr179);
                    b=expr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    value = a*b;

                    }
                    break;
                case 4 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:34:9: ID
                    {
                    ID4=(CommonTree)match(input,ID,FOLLOW_ID_in_expr192); 

                            Integer v = (Integer)memory.get((ID4!=null?ID4.getText():null));
                            if ( v!=null ) value = v.intValue();
                            else System.err.println("undefined variable "+(ID4!=null?ID4.getText():null));
                            

                    }
                    break;
                case 5 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Eval.g:40:9: INT
                    {
                    INT5=(CommonTree)match(input,INT,FOLLOW_INT_in_expr213); 
                    value = Integer.parseInt((INT5!=null?INT5.getText():null));

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
        return value;
    }
    // $ANTLR end "expr"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog51 = new BitSet(new long[]{0x0000000000000F62L});
    public static final BitSet FOLLOW_expr_in_stat62 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_8_in_stat83 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_stat85 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_stat87 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_9_in_expr124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr128 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_expr132 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_10_in_expr146 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr150 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_expr154 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_11_in_expr171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr175 = new BitSet(new long[]{0x0000000000000E60L});
    public static final BitSet FOLLOW_expr_in_expr179 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_expr192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_expr213 = new BitSet(new long[]{0x0000000000000002L});

}