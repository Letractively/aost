// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g 2009-09-14 22:53:47

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.debug.*;
import java.io.IOException;
public class ExprParser extends DebugParser {
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

    public static final String[] ruleNames = new String[] {
        "invalidRule", "atom", "prog", "expr", "multExpr", "stat"
    };
     
        public int ruleLevel = 0;
        public int getRuleLevel() { return ruleLevel; }
        public void incRuleLevel() { ruleLevel++; }
        public void decRuleLevel() { ruleLevel--; }
        public ExprParser(TokenStream input) {
            this(input, DebugEventSocketProxy.DEFAULT_DEBUGGER_PORT, new RecognizerSharedState());
        }
        public ExprParser(TokenStream input, int port, RecognizerSharedState state) {
            super(input, state);
            DebugEventSocketProxy proxy =
                new DebugEventSocketProxy(this, port, null);
            setDebugListener(proxy);
            try {
                proxy.handshake();
            }
            catch (IOException ioe) {
                reportError(ioe);
            }
        }
    public ExprParser(TokenStream input, DebugEventListener dbg) {
        super(input, dbg, new RecognizerSharedState());

    }
    protected boolean evalPredicate(boolean result, String predicate) {
        dbg.semanticPredicate(result, predicate);
        return result;
    }


    public String[] getTokenNames() { return ExprParser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g"; }



    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:4:1: prog : ( stat )+ ;
    public final void prog() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "prog");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(4, 1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:4:5: ( ( stat )+ )
            dbg.enterAlt(1);

            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:4:9: ( stat )+
            {
            dbg.location(4,9);
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:4:9: ( stat )+
            int cnt1=0;
            try { dbg.enterSubRule(1);

            loop1:
            do {
                int alt1=2;
                try { dbg.enterDecision(1);

                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=INT)||LA1_0==12) ) {
                    alt1=1;
                }


                } finally {dbg.exitDecision(1);}

                switch (alt1) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:4:9: stat
            	    {
            	    dbg.location(4,9);
            	    pushFollow(FOLLOW_stat_in_prog12);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        dbg.recognitionException(eee);

                        throw eee;
                }
                cnt1++;
            } while (true);
            } finally {dbg.exitSubRule(1);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(4, 15);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "prog");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "prog"


    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:6:1: stat : ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE );
    public final void stat() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "stat");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(6, 1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:6:5: ( expr NEWLINE | ID '=' expr NEWLINE | NEWLINE )
            int alt2=3;
            try { dbg.enterDecision(2);

            switch ( input.LA(1) ) {
            case INT:
            case 12:
                {
                alt2=1;
                }
                break;
            case ID:
                {
                int LA2_2 = input.LA(2);

                if ( (LA2_2==8) ) {
                    alt2=2;
                }
                else if ( (LA2_2==NEWLINE||(LA2_2>=9 && LA2_2<=11)) ) {
                    alt2=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 2, input);

                    dbg.recognitionException(nvae);
                    throw nvae;
                }
                }
                break;
            case NEWLINE:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(2);}

            switch (alt2) {
                case 1 :
                    dbg.enterAlt(1);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:6:9: expr NEWLINE
                    {
                    dbg.location(6,9);
                    pushFollow(FOLLOW_expr_in_stat23);
                    expr();

                    state._fsp--;

                    dbg.location(6,14);
                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat25); 

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:7:9: ID '=' expr NEWLINE
                    {
                    dbg.location(7,9);
                    match(input,ID,FOLLOW_ID_in_stat35); 
                    dbg.location(7,12);
                    match(input,8,FOLLOW_8_in_stat37); 
                    dbg.location(7,16);
                    pushFollow(FOLLOW_expr_in_stat39);
                    expr();

                    state._fsp--;

                    dbg.location(7,21);
                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat41); 

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:8:9: NEWLINE
                    {
                    dbg.location(8,9);
                    match(input,NEWLINE,FOLLOW_NEWLINE_in_stat51); 

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
        dbg.location(9, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "stat");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "stat"


    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:13:1: expr : multExpr ( ( '+' | '-' ) multExpr )* ;
    public final void expr() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "expr");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(13, 1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:13:5: ( multExpr ( ( '+' | '-' ) multExpr )* )
            dbg.enterAlt(1);

            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:13:9: multExpr ( ( '+' | '-' ) multExpr )*
            {
            dbg.location(13,9);
            pushFollow(FOLLOW_multExpr_in_expr67);
            multExpr();

            state._fsp--;

            dbg.location(13,18);
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:13:18: ( ( '+' | '-' ) multExpr )*
            try { dbg.enterSubRule(3);

            loop3:
            do {
                int alt3=2;
                try { dbg.enterDecision(3);

                int LA3_0 = input.LA(1);

                if ( ((LA3_0>=9 && LA3_0<=10)) ) {
                    alt3=1;
                }


                } finally {dbg.exitDecision(3);}

                switch (alt3) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:13:19: ( '+' | '-' ) multExpr
            	    {
            	    dbg.location(13,19);
            	    if ( (input.LA(1)>=9 && input.LA(1)<=10) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        dbg.recognitionException(mse);
            	        throw mse;
            	    }

            	    dbg.location(13,29);
            	    pushFollow(FOLLOW_multExpr_in_expr76);
            	    multExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);
            } finally {dbg.exitSubRule(3);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(14, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "expr");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "expr"


    // $ANTLR start "multExpr"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:16:1: multExpr : atom ( '*' atom )* ;
    public final void multExpr() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "multExpr");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(16, 1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:17:5: ( atom ( '*' atom )* )
            dbg.enterAlt(1);

            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:17:9: atom ( '*' atom )*
            {
            dbg.location(17,9);
            pushFollow(FOLLOW_atom_in_multExpr98);
            atom();

            state._fsp--;

            dbg.location(17,14);
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:17:14: ( '*' atom )*
            try { dbg.enterSubRule(4);

            loop4:
            do {
                int alt4=2;
                try { dbg.enterDecision(4);

                int LA4_0 = input.LA(1);

                if ( (LA4_0==11) ) {
                    alt4=1;
                }


                } finally {dbg.exitDecision(4);}

                switch (alt4) {
            	case 1 :
            	    dbg.enterAlt(1);

            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:17:15: '*' atom
            	    {
            	    dbg.location(17,15);
            	    match(input,11,FOLLOW_11_in_multExpr101); 
            	    dbg.location(17,19);
            	    pushFollow(FOLLOW_atom_in_multExpr103);
            	    atom();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);
            } finally {dbg.exitSubRule(4);}


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        dbg.location(18, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "multExpr");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "multExpr"


    // $ANTLR start "atom"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:20:1: atom : ( INT | ID | '(' expr ')' );
    public final void atom() throws RecognitionException {
        try { dbg.enterRule(getGrammarFileName(), "atom");
        if ( getRuleLevel()==0 ) {dbg.commence();}
        incRuleLevel();
        dbg.location(20, 1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:20:5: ( INT | ID | '(' expr ')' )
            int alt5=3;
            try { dbg.enterDecision(5);

            switch ( input.LA(1) ) {
            case INT:
                {
                alt5=1;
                }
                break;
            case ID:
                {
                alt5=2;
                }
                break;
            case 12:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                dbg.recognitionException(nvae);
                throw nvae;
            }

            } finally {dbg.exitDecision(5);}

            switch (alt5) {
                case 1 :
                    dbg.enterAlt(1);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:20:9: INT
                    {
                    dbg.location(20,9);
                    match(input,INT,FOLLOW_INT_in_atom120); 

                    }
                    break;
                case 2 :
                    dbg.enterAlt(2);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:21:9: ID
                    {
                    dbg.location(21,9);
                    match(input,ID,FOLLOW_ID_in_atom131); 

                    }
                    break;
                case 3 :
                    dbg.enterAlt(3);

                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/Expr.g:22:9: '(' expr ')'
                    {
                    dbg.location(22,9);
                    match(input,12,FOLLOW_12_in_atom141); 
                    dbg.location(22,13);
                    pushFollow(FOLLOW_expr_in_atom143);
                    expr();

                    state._fsp--;

                    dbg.location(22,18);
                    match(input,13,FOLLOW_13_in_atom145); 

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
        dbg.location(23, 5);

        }
        finally {
            dbg.exitRule(getGrammarFileName(), "atom");
            decRuleLevel();
            if ( getRuleLevel()==0 ) {dbg.terminate();}
        }

        return ;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog12 = new BitSet(new long[]{0x0000000000001072L});
    public static final BitSet FOLLOW_expr_in_stat23 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat25 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat35 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_stat37 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_expr_in_stat39 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat41 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat51 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_expr67 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_set_in_expr70 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_multExpr_in_expr76 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_atom_in_multExpr98 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_multExpr101 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_atom_in_multExpr103 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_INT_in_atom120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_atom141 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_expr_in_atom143 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_atom145 = new BitSet(new long[]{0x0000000000000002L});

}