// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g 2009-09-16 16:11:07

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class CMinusWalker extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "VAR", "FUNC", "ARG", "SLIST", "ID", "INT", "WS", "';'", "'int'", "'char'", "'('", "','", "')'", "'{'", "'}'", "'for'", "'='", "'=='", "'!='", "'+'", "'*'"
    };
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int T__20=20;
    public static final int INT=9;
    public static final int ID=8;
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int WS=10;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int ARG=6;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int FUNC=5;
    public static final int T__13=13;
    public static final int SLIST=7;
    public static final int VAR=4;

    // delegates
    // delegators


        public CMinusWalker(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public CMinusWalker(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return CMinusWalker.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g"; }



    // $ANTLR start "program"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:12:1: program : ( declaration )+ ;
    public final void program() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:13:5: ( ( declaration )+ )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:13:9: ( declaration )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:13:9: ( declaration )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=VAR && LA1_0<=FUNC)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:13:9: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_program42);
            	    declaration();

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
    // $ANTLR end "program"


    // $ANTLR start "declaration"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:16:1: declaration : ( variable | function );
    public final void declaration() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:17:5: ( variable | function )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==VAR) ) {
                alt2=1;
            }
            else if ( (LA2_0==FUNC) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:17:9: variable
                    {
                    pushFollow(FOLLOW_variable_in_declaration62);
                    variable();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:18:9: function
                    {
                    pushFollow(FOLLOW_function_in_declaration72);
                    function();

                    state._fsp--;


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
    // $ANTLR end "declaration"


    // $ANTLR start "variable"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:21:1: variable : ^( VAR type ID ) ;
    public final void variable() throws RecognitionException {
        CommonTree ID2=null;
        CMinusWalker.type_return type1 = null;


        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:22:5: ( ^( VAR type ID ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:22:9: ^( VAR type ID )
            {
            match(input,VAR,FOLLOW_VAR_in_variable92); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_variable94);
            type1=type();

            state._fsp--;

            ID2=(CommonTree)match(input,ID,FOLLOW_ID_in_variable96); 

            match(input, Token.UP, null); 
            System.out.println("define "+(type1!=null?(input.getTokenStream().toString(
              input.getTreeAdaptor().getTokenStartIndex(type1.start),
              input.getTreeAdaptor().getTokenStopIndex(type1.start))):null)+" "+(ID2!=null?ID2.getText():null));

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
    // $ANTLR end "variable"

    public static class type_return extends TreeRuleReturnScope {
    };

    // $ANTLR start "type"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:26:1: type : ( 'int' | 'char' );
    public final CMinusWalker.type_return type() throws RecognitionException {
        CMinusWalker.type_return retval = new CMinusWalker.type_return();
        retval.start = input.LT(1);

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:26:5: ( 'int' | 'char' )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:
            {
            if ( (input.LA(1)>=12 && input.LA(1)<=13) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "type"


    // $ANTLR start "function"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:30:1: function : ^( FUNC type ID ( formalParameter )* block ) ;
    public final void function() throws RecognitionException {
        CommonTree ID4=null;
        CMinusWalker.type_return type3 = null;


        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:31:5: ( ^( FUNC type ID ( formalParameter )* block ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:31:9: ^( FUNC type ID ( formalParameter )* block )
            {
            match(input,FUNC,FOLLOW_FUNC_in_function152); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_function154);
            type3=type();

            state._fsp--;

            ID4=(CommonTree)match(input,ID,FOLLOW_ID_in_function156); 
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:31:24: ( formalParameter )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==ARG) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:31:24: formalParameter
            	    {
            	    pushFollow(FOLLOW_formalParameter_in_function158);
            	    formalParameter();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_block_in_function161);
            block();

            state._fsp--;


            match(input, Token.UP, null); 
            System.out.println("define "+(type3!=null?(input.getTokenStream().toString(
              input.getTreeAdaptor().getTokenStartIndex(type3.start),
              input.getTreeAdaptor().getTokenStopIndex(type3.start))):null)+" "+(ID4!=null?ID4.getText():null)+"()");

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
    // $ANTLR end "function"


    // $ANTLR start "formalParameter"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:35:1: formalParameter : ^( ARG type ID ) ;
    public final void formalParameter() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:36:5: ( ^( ARG type ID ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:36:9: ^( ARG type ID )
            {
            match(input,ARG,FOLLOW_ARG_in_formalParameter192); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_type_in_formalParameter194);
            type();

            state._fsp--;

            match(input,ID,FOLLOW_ID_in_formalParameter196); 

            match(input, Token.UP, null); 

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
    // $ANTLR end "formalParameter"


    // $ANTLR start "block"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:41:1: block : ^( SLIST ( variable )* ( stat )* ) ;
    public final void block() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:5: ( ^( SLIST ( variable )* ( stat )* ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:9: ^( SLIST ( variable )* ( stat )* )
            {
            match(input,SLIST,FOLLOW_SLIST_in_block219); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:17: ( variable )*
                loop4:
                do {
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0==VAR) ) {
                        alt4=1;
                    }


                    switch (alt4) {
                	case 1 :
                	    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:17: variable
                	    {
                	    pushFollow(FOLLOW_variable_in_block221);
                	    variable();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop4;
                    }
                } while (true);

                // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:27: ( stat )*
                loop5:
                do {
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( ((LA5_0>=SLIST && LA5_0<=INT)||(LA5_0>=19 && LA5_0<=24)) ) {
                        alt5=1;
                    }


                    switch (alt5) {
                	case 1 :
                	    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:42:27: stat
                	    {
                	    pushFollow(FOLLOW_stat_in_block224);
                	    stat();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop5;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

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
    // $ANTLR end "block"


    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:45:1: stat : ( forStat | expr | block | assignStat );
    public final void stat() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:45:5: ( forStat | expr | block | assignStat )
            int alt6=4;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt6=1;
                }
                break;
            case ID:
            case INT:
            case 21:
            case 22:
            case 23:
            case 24:
                {
                alt6=2;
                }
                break;
            case SLIST:
                {
                alt6=3;
                }
                break;
            case 20:
                {
                alt6=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:45:7: forStat
                    {
                    pushFollow(FOLLOW_forStat_in_stat238);
                    forStat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:46:7: expr
                    {
                    pushFollow(FOLLOW_expr_in_stat246);
                    expr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:47:7: block
                    {
                    pushFollow(FOLLOW_block_in_stat254);
                    block();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:48:7: assignStat
                    {
                    pushFollow(FOLLOW_assignStat_in_stat262);
                    assignStat();

                    state._fsp--;


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


    // $ANTLR start "forStat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:51:1: forStat : ^( 'for' assignStat expr assignStat block ) ;
    public final void forStat() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:52:5: ( ^( 'for' assignStat expr assignStat block ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:52:9: ^( 'for' assignStat expr assignStat block )
            {
            match(input,19,FOLLOW_19_in_forStat282); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_assignStat_in_forStat284);
            assignStat();

            state._fsp--;

            pushFollow(FOLLOW_expr_in_forStat286);
            expr();

            state._fsp--;

            pushFollow(FOLLOW_assignStat_in_forStat288);
            assignStat();

            state._fsp--;

            pushFollow(FOLLOW_block_in_forStat290);
            block();

            state._fsp--;


            match(input, Token.UP, null); 

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
    // $ANTLR end "forStat"


    // $ANTLR start "assignStat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:55:1: assignStat : ^( '=' ID expr ) ;
    public final void assignStat() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:56:5: ( ^( '=' ID expr ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:56:9: ^( '=' ID expr )
            {
            match(input,20,FOLLOW_20_in_assignStat311); 

            match(input, Token.DOWN, null); 
            match(input,ID,FOLLOW_ID_in_assignStat313); 
            pushFollow(FOLLOW_expr_in_assignStat315);
            expr();

            state._fsp--;


            match(input, Token.UP, null); 

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
    // $ANTLR end "assignStat"


    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:61:1: expr : ( ^( '==' expr expr ) | ^( '!=' expr expr ) | ^( '+' expr expr ) | ^( '*' expr expr ) | ID | INT );
    public final void expr() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:61:5: ( ^( '==' expr expr ) | ^( '!=' expr expr ) | ^( '+' expr expr ) | ^( '*' expr expr ) | ID | INT )
            int alt7=6;
            switch ( input.LA(1) ) {
            case 21:
                {
                alt7=1;
                }
                break;
            case 22:
                {
                alt7=2;
                }
                break;
            case 23:
                {
                alt7=3;
                }
                break;
            case 24:
                {
                alt7=4;
                }
                break;
            case ID:
                {
                alt7=5;
                }
                break;
            case INT:
                {
                alt7=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:61:9: ^( '==' expr expr )
                    {
                    match(input,21,FOLLOW_21_in_expr333); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr335);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr337);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:62:9: ^( '!=' expr expr )
                    {
                    match(input,22,FOLLOW_22_in_expr349); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr351);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr353);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:63:9: ^( '+' expr expr )
                    {
                    match(input,23,FOLLOW_23_in_expr365); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr367);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr369);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:64:9: ^( '*' expr expr )
                    {
                    match(input,24,FOLLOW_24_in_expr381); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_expr_in_expr383);
                    expr();

                    state._fsp--;

                    pushFollow(FOLLOW_expr_in_expr385);
                    expr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:65:9: ID
                    {
                    match(input,ID,FOLLOW_ID_in_expr396); 

                    }
                    break;
                case 6 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinusWalker.g:66:9: INT
                    {
                    match(input,INT,FOLLOW_INT_in_expr406); 

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


 

    public static final BitSet FOLLOW_declaration_in_program42 = new BitSet(new long[]{0x0000000000000032L});
    public static final BitSet FOLLOW_variable_in_declaration62 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_declaration72 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VAR_in_variable92 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_variable94 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_variable96 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FUNC_in_function152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_function154 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_function156 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_formalParameter_in_function158 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_block_in_function161 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ARG_in_formalParameter192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_type_in_formalParameter194 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_formalParameter196 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SLIST_in_block219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_variable_in_block221 = new BitSet(new long[]{0x0000000001F803D8L});
    public static final BitSet FOLLOW_stat_in_block224 = new BitSet(new long[]{0x0000000001F803C8L});
    public static final BitSet FOLLOW_forStat_in_stat238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_stat246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignStat_in_stat262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_forStat282 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_assignStat_in_forStat284 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_forStat286 = new BitSet(new long[]{0x0000000001F803C0L});
    public static final BitSet FOLLOW_assignStat_in_forStat288 = new BitSet(new long[]{0x00000000000000C0L});
    public static final BitSet FOLLOW_block_in_forStat290 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_20_in_assignStat311 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ID_in_assignStat313 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_assignStat315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_21_in_expr333 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr335 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_expr337 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_22_in_expr349 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr351 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_expr353 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_23_in_expr365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr367 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_expr369 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_24_in_expr381 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_expr_in_expr383 = new BitSet(new long[]{0x0000000001E00300L});
    public static final BitSet FOLLOW_expr_in_expr385 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ID_in_expr396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_expr406 = new BitSet(new long[]{0x0000000000000002L});

}