// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/CMinus.g 2009-09-16 15:43:51

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

/**  Recognize and build trees for C-
 *   Results in CMinusParser.java, CMinusLexer.java,
 *   and the token definition file CMinus.tokens used by
 *   the tree grammar to ensure token types are the same.
 */
public class CMinusParser extends Parser {
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
    public static final int T__13=13;
    public static final int FUNC=5;
    public static final int SLIST=7;
    public static final int VAR=4;

    // delegates
    // delegators


        public CMinusParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public CMinusParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return CMinusParser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/CMinus.g"; }


    public static class program_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:21:1: program : ( declaration )+ ;
    public final CMinusParser.program_return program() throws RecognitionException {
        CMinusParser.program_return retval = new CMinusParser.program_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CMinusParser.declaration_return declaration1 = null;



        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:22:5: ( ( declaration )+ )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:22:9: ( declaration )+
            {
            root_0 = (Object)adaptor.nil();

            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:22:9: ( declaration )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=12 && LA1_0<=13)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:22:9: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_program65);
            	    declaration1=declaration();

            	    state._fsp--;

            	    adaptor.addChild(root_0, declaration1.getTree());

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
    // $ANTLR end "program"

    public static class declaration_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "declaration"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:25:1: declaration : ( variable | function );
    public final CMinusParser.declaration_return declaration() throws RecognitionException {
        CMinusParser.declaration_return retval = new CMinusParser.declaration_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CMinusParser.variable_return variable2 = null;

        CMinusParser.function_return function3 = null;



        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:26:5: ( variable | function )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=12 && LA2_0<=13)) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==ID) ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2==14) ) {
                        alt2=2;
                    }
                    else if ( (LA2_2==11) ) {
                        alt2=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:26:9: variable
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_variable_in_declaration85);
                    variable2=variable();

                    state._fsp--;

                    adaptor.addChild(root_0, variable2.getTree());

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:27:9: function
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_function_in_declaration95);
                    function3=function();

                    state._fsp--;

                    adaptor.addChild(root_0, function3.getTree());

                    }
                    break;

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
    // $ANTLR end "declaration"

    public static class variable_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "variable"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:30:1: variable : type ID ';' -> ^( VAR type ID ) ;
    public final CMinusParser.variable_return variable() throws RecognitionException {
        CMinusParser.variable_return retval = new CMinusParser.variable_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID5=null;
        Token char_literal6=null;
        CMinusParser.type_return type4 = null;


        Object ID5_tree=null;
        Object char_literal6_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_11=new RewriteRuleTokenStream(adaptor,"token 11");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:31:5: ( type ID ';' -> ^( VAR type ID ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:31:9: type ID ';'
            {
            pushFollow(FOLLOW_type_in_variable114);
            type4=type();

            state._fsp--;

            stream_type.add(type4.getTree());
            ID5=(Token)match(input,ID,FOLLOW_ID_in_variable116);  
            stream_ID.add(ID5);

            char_literal6=(Token)match(input,11,FOLLOW_11_in_variable118);  
            stream_11.add(char_literal6);



            // AST REWRITE
            // elements: type, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 31:21: -> ^( VAR type ID )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:31:24: ^( VAR type ID )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(VAR, "VAR"), root_1);

                adaptor.addChild(root_1, stream_type.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "variable"

    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "type"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:34:1: type : ( 'int' | 'char' );
    public final CMinusParser.type_return type() throws RecognitionException {
        CMinusParser.type_return retval = new CMinusParser.type_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set7=null;

        Object set7_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:34:5: ( 'int' | 'char' )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:
            {
            root_0 = (Object)adaptor.nil();

            set7=(Token)input.LT(1);
            if ( (input.LA(1)>=12 && input.LA(1)<=13) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set7));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


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
    // $ANTLR end "type"

    public static class function_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "function"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:41:1: function : type ID '(' ( formalParameter ( ',' formalParameter )* )? ')' block -> ^( FUNC type ID ( formalParameter )* block ) ;
    public final CMinusParser.function_return function() throws RecognitionException {
        CMinusParser.function_return retval = new CMinusParser.function_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID9=null;
        Token char_literal10=null;
        Token char_literal12=null;
        Token char_literal14=null;
        CMinusParser.type_return type8 = null;

        CMinusParser.formalParameter_return formalParameter11 = null;

        CMinusParser.formalParameter_return formalParameter13 = null;

        CMinusParser.block_return block15 = null;


        Object ID9_tree=null;
        Object char_literal10_tree=null;
        Object char_literal12_tree=null;
        Object char_literal14_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_15=new RewriteRuleTokenStream(adaptor,"token 15");
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleTokenStream stream_14=new RewriteRuleTokenStream(adaptor,"token 14");
        RewriteRuleSubtreeStream stream_formalParameter=new RewriteRuleSubtreeStream(adaptor,"rule formalParameter");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:42:5: ( type ID '(' ( formalParameter ( ',' formalParameter )* )? ')' block -> ^( FUNC type ID ( formalParameter )* block ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:42:9: type ID '(' ( formalParameter ( ',' formalParameter )* )? ')' block
            {
            pushFollow(FOLLOW_type_in_function175);
            type8=type();

            state._fsp--;

            stream_type.add(type8.getTree());
            ID9=(Token)match(input,ID,FOLLOW_ID_in_function177);  
            stream_ID.add(ID9);

            char_literal10=(Token)match(input,14,FOLLOW_14_in_function187);  
            stream_14.add(char_literal10);

            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:43:13: ( formalParameter ( ',' formalParameter )* )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( ((LA4_0>=12 && LA4_0<=13)) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:43:15: formalParameter ( ',' formalParameter )*
                    {
                    pushFollow(FOLLOW_formalParameter_in_function191);
                    formalParameter11=formalParameter();

                    state._fsp--;

                    stream_formalParameter.add(formalParameter11.getTree());
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:43:31: ( ',' formalParameter )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==15) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:43:32: ',' formalParameter
                    	    {
                    	    char_literal12=(Token)match(input,15,FOLLOW_15_in_function194);  
                    	    stream_15.add(char_literal12);

                    	    pushFollow(FOLLOW_formalParameter_in_function196);
                    	    formalParameter13=formalParameter();

                    	    state._fsp--;

                    	    stream_formalParameter.add(formalParameter13.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal14=(Token)match(input,16,FOLLOW_16_in_function204);  
            stream_16.add(char_literal14);

            pushFollow(FOLLOW_block_in_function214);
            block15=block();

            state._fsp--;

            stream_block.add(block15.getTree());


            // AST REWRITE
            // elements: formalParameter, ID, block, type
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 45:9: -> ^( FUNC type ID ( formalParameter )* block )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:45:12: ^( FUNC type ID ( formalParameter )* block )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(FUNC, "FUNC"), root_1);

                adaptor.addChild(root_1, stream_type.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:45:27: ( formalParameter )*
                while ( stream_formalParameter.hasNext() ) {
                    adaptor.addChild(root_1, stream_formalParameter.nextTree());

                }
                stream_formalParameter.reset();
                adaptor.addChild(root_1, stream_block.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "function"

    public static class formalParameter_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "formalParameter"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:48:1: formalParameter : type ID -> ^( ARG type ID ) ;
    public final CMinusParser.formalParameter_return formalParameter() throws RecognitionException {
        CMinusParser.formalParameter_return retval = new CMinusParser.formalParameter_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID17=null;
        CMinusParser.type_return type16 = null;


        Object ID17_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_type=new RewriteRuleSubtreeStream(adaptor,"rule type");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:49:5: ( type ID -> ^( ARG type ID ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:49:9: type ID
            {
            pushFollow(FOLLOW_type_in_formalParameter256);
            type16=type();

            state._fsp--;

            stream_type.add(type16.getTree());
            ID17=(Token)match(input,ID,FOLLOW_ID_in_formalParameter258);  
            stream_ID.add(ID17);



            // AST REWRITE
            // elements: type, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 49:17: -> ^( ARG type ID )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:49:20: ^( ARG type ID )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ARG, "ARG"), root_1);

                adaptor.addChild(root_1, stream_type.nextTree());
                adaptor.addChild(root_1, stream_ID.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "formalParameter"

    public static class block_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "block"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:55:1: block : lc= '{' ( variable )* ( stat )* '}' -> ^( SLIST[$lc,\"SLIST\"] ( variable )* ( stat )* ) ;
    public final CMinusParser.block_return block() throws RecognitionException {
        CMinusParser.block_return retval = new CMinusParser.block_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token lc=null;
        Token char_literal20=null;
        CMinusParser.variable_return variable18 = null;

        CMinusParser.stat_return stat19 = null;


        Object lc_tree=null;
        Object char_literal20_tree=null;
        RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
        RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
        RewriteRuleSubtreeStream stream_variable=new RewriteRuleSubtreeStream(adaptor,"rule variable");
        RewriteRuleSubtreeStream stream_stat=new RewriteRuleSubtreeStream(adaptor,"rule stat");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:5: (lc= '{' ( variable )* ( stat )* '}' -> ^( SLIST[$lc,\"SLIST\"] ( variable )* ( stat )* ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:9: lc= '{' ( variable )* ( stat )* '}'
            {
            lc=(Token)match(input,17,FOLLOW_17_in_block292);  
            stream_17.add(lc);

            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:16: ( variable )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>=12 && LA5_0<=13)) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:16: variable
            	    {
            	    pushFollow(FOLLOW_variable_in_block294);
            	    variable18=variable();

            	    state._fsp--;

            	    stream_variable.add(variable18.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:26: ( stat )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>=ID && LA6_0<=INT)||LA6_0==11||LA6_0==14||LA6_0==17||LA6_0==19) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:56:26: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_block297);
            	    stat19=stat();

            	    state._fsp--;

            	    stream_stat.add(stat19.getTree());

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            char_literal20=(Token)match(input,18,FOLLOW_18_in_block300);  
            stream_18.add(char_literal20);



            // AST REWRITE
            // elements: variable, stat
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 57:9: -> ^( SLIST[$lc,\"SLIST\"] ( variable )* ( stat )* )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:57:12: ^( SLIST[$lc,\"SLIST\"] ( variable )* ( stat )* )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(SLIST, lc, "SLIST"), root_1);

                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:57:33: ( variable )*
                while ( stream_variable.hasNext() ) {
                    adaptor.addChild(root_1, stream_variable.nextTree());

                }
                stream_variable.reset();
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:57:43: ( stat )*
                while ( stream_stat.hasNext() ) {
                    adaptor.addChild(root_1, stream_stat.nextTree());

                }
                stream_stat.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "block"

    public static class stat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:60:1: stat : ( forStat | expr ';' | block | assignStat ';' | ';' );
    public final CMinusParser.stat_return stat() throws RecognitionException {
        CMinusParser.stat_return retval = new CMinusParser.stat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal23=null;
        Token char_literal26=null;
        Token char_literal27=null;
        CMinusParser.forStat_return forStat21 = null;

        CMinusParser.expr_return expr22 = null;

        CMinusParser.block_return block24 = null;

        CMinusParser.assignStat_return assignStat25 = null;


        Object char_literal23_tree=null;
        Object char_literal26_tree=null;
        Object char_literal27_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:60:5: ( forStat | expr ';' | block | assignStat ';' | ';' )
            int alt7=5;
            switch ( input.LA(1) ) {
            case 19:
                {
                alt7=1;
                }
                break;
            case ID:
                {
                int LA7_2 = input.LA(2);

                if ( (LA7_2==20) ) {
                    alt7=4;
                }
                else if ( (LA7_2==11||(LA7_2>=21 && LA7_2<=24)) ) {
                    alt7=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 2, input);

                    throw nvae;
                }
                }
                break;
            case INT:
            case 14:
                {
                alt7=2;
                }
                break;
            case 17:
                {
                alt7=3;
                }
                break;
            case 11:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:60:7: forStat
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_forStat_in_stat333);
                    forStat21=forStat();

                    state._fsp--;

                    adaptor.addChild(root_0, forStat21.getTree());

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:61:7: expr ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_stat341);
                    expr22=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr22.getTree());
                    char_literal23=(Token)match(input,11,FOLLOW_11_in_stat343); 

                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:62:7: block
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_block_in_stat352);
                    block24=block();

                    state._fsp--;

                    adaptor.addChild(root_0, block24.getTree());

                    }
                    break;
                case 4 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:63:7: assignStat ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_assignStat_in_stat360);
                    assignStat25=assignStat();

                    state._fsp--;

                    adaptor.addChild(root_0, assignStat25.getTree());
                    char_literal26=(Token)match(input,11,FOLLOW_11_in_stat362); 

                    }
                    break;
                case 5 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:64:7: ';'
                    {
                    root_0 = (Object)adaptor.nil();

                    char_literal27=(Token)match(input,11,FOLLOW_11_in_stat371); 

                    }
                    break;

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
    // $ANTLR end "stat"

    public static class forStat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "forStat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:67:1: forStat : 'for' '(' first= assignStat ';' expr ';' inc= assignStat ')' block -> ^( 'for' $first expr $inc block ) ;
    public final CMinusParser.forStat_return forStat() throws RecognitionException {
        CMinusParser.forStat_return retval = new CMinusParser.forStat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal28=null;
        Token char_literal29=null;
        Token char_literal30=null;
        Token char_literal32=null;
        Token char_literal33=null;
        CMinusParser.assignStat_return first = null;

        CMinusParser.assignStat_return inc = null;

        CMinusParser.expr_return expr31 = null;

        CMinusParser.block_return block34 = null;


        Object string_literal28_tree=null;
        Object char_literal29_tree=null;
        Object char_literal30_tree=null;
        Object char_literal32_tree=null;
        Object char_literal33_tree=null;
        RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleTokenStream stream_14=new RewriteRuleTokenStream(adaptor,"token 14");
        RewriteRuleTokenStream stream_11=new RewriteRuleTokenStream(adaptor,"token 11");
        RewriteRuleSubtreeStream stream_assignStat=new RewriteRuleSubtreeStream(adaptor,"rule assignStat");
        RewriteRuleSubtreeStream stream_block=new RewriteRuleSubtreeStream(adaptor,"rule block");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:68:5: ( 'for' '(' first= assignStat ';' expr ';' inc= assignStat ')' block -> ^( 'for' $first expr $inc block ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:68:9: 'for' '(' first= assignStat ';' expr ';' inc= assignStat ')' block
            {
            string_literal28=(Token)match(input,19,FOLLOW_19_in_forStat391);  
            stream_19.add(string_literal28);

            char_literal29=(Token)match(input,14,FOLLOW_14_in_forStat393);  
            stream_14.add(char_literal29);

            pushFollow(FOLLOW_assignStat_in_forStat397);
            first=assignStat();

            state._fsp--;

            stream_assignStat.add(first.getTree());
            char_literal30=(Token)match(input,11,FOLLOW_11_in_forStat399);  
            stream_11.add(char_literal30);

            pushFollow(FOLLOW_expr_in_forStat401);
            expr31=expr();

            state._fsp--;

            stream_expr.add(expr31.getTree());
            char_literal32=(Token)match(input,11,FOLLOW_11_in_forStat403);  
            stream_11.add(char_literal32);

            pushFollow(FOLLOW_assignStat_in_forStat407);
            inc=assignStat();

            state._fsp--;

            stream_assignStat.add(inc.getTree());
            char_literal33=(Token)match(input,16,FOLLOW_16_in_forStat409);  
            stream_16.add(char_literal33);

            pushFollow(FOLLOW_block_in_forStat411);
            block34=block();

            state._fsp--;

            stream_block.add(block34.getTree());


            // AST REWRITE
            // elements: block, first, 19, expr, inc
            // token labels: 
            // rule labels: retval, inc, first
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_inc=new RewriteRuleSubtreeStream(adaptor,"rule inc",inc!=null?inc.tree:null);
            RewriteRuleSubtreeStream stream_first=new RewriteRuleSubtreeStream(adaptor,"rule first",first!=null?first.tree:null);

            root_0 = (Object)adaptor.nil();
            // 69:9: -> ^( 'for' $first expr $inc block )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:69:12: ^( 'for' $first expr $inc block )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_19.nextNode(), root_1);

                adaptor.addChild(root_1, stream_first.nextTree());
                adaptor.addChild(root_1, stream_expr.nextTree());
                adaptor.addChild(root_1, stream_inc.nextTree());
                adaptor.addChild(root_1, stream_block.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "forStat"

    public static class assignStat_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignStat"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:72:1: assignStat : ID '=' expr -> ^( '=' ID expr ) ;
    public final CMinusParser.assignStat_return assignStat() throws RecognitionException {
        CMinusParser.assignStat_return retval = new CMinusParser.assignStat_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID35=null;
        Token char_literal36=null;
        CMinusParser.expr_return expr37 = null;


        Object ID35_tree=null;
        Object char_literal36_tree=null;
        RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:73:5: ( ID '=' expr -> ^( '=' ID expr ) )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:73:9: ID '=' expr
            {
            ID35=(Token)match(input,ID,FOLLOW_ID_in_assignStat454);  
            stream_ID.add(ID35);

            char_literal36=(Token)match(input,20,FOLLOW_20_in_assignStat456);  
            stream_20.add(char_literal36);

            pushFollow(FOLLOW_expr_in_assignStat458);
            expr37=expr();

            state._fsp--;

            stream_expr.add(expr37.getTree());


            // AST REWRITE
            // elements: 20, expr, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 73:21: -> ^( '=' ID expr )
            {
                // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:73:24: ^( '=' ID expr )
                {
                Object root_1 = (Object)adaptor.nil();
                root_1 = (Object)adaptor.becomeRoot(stream_20.nextNode(), root_1);

                adaptor.addChild(root_1, stream_ID.nextNode());
                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;
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
    // $ANTLR end "assignStat"

    public static class expr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:78:1: expr : condExpr ;
    public final CMinusParser.expr_return expr() throws RecognitionException {
        CMinusParser.expr_return retval = new CMinusParser.expr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        CMinusParser.condExpr_return condExpr38 = null;



        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:78:5: ( condExpr )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:78:9: condExpr
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_condExpr_in_expr484);
            condExpr38=condExpr();

            state._fsp--;

            adaptor.addChild(root_0, condExpr38.getTree());

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

    public static class condExpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condExpr"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:80:1: condExpr : aexpr ( ( '==' | '!=' ) aexpr )? ;
    public final CMinusParser.condExpr_return condExpr() throws RecognitionException {
        CMinusParser.condExpr_return retval = new CMinusParser.condExpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token string_literal40=null;
        Token string_literal41=null;
        CMinusParser.aexpr_return aexpr39 = null;

        CMinusParser.aexpr_return aexpr42 = null;


        Object string_literal40_tree=null;
        Object string_literal41_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:5: ( aexpr ( ( '==' | '!=' ) aexpr )? )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:9: aexpr ( ( '==' | '!=' ) aexpr )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_aexpr_in_condExpr499);
            aexpr39=aexpr();

            state._fsp--;

            adaptor.addChild(root_0, aexpr39.getTree());
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:15: ( ( '==' | '!=' ) aexpr )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=21 && LA9_0<=22)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:17: ( '==' | '!=' ) aexpr
                    {
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:17: ( '==' | '!=' )
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==21) ) {
                        alt8=1;
                    }
                    else if ( (LA8_0==22) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 0, input);

                        throw nvae;
                    }
                    switch (alt8) {
                        case 1 :
                            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:18: '=='
                            {
                            string_literal40=(Token)match(input,21,FOLLOW_21_in_condExpr504); 
                            string_literal40_tree = (Object)adaptor.create(string_literal40);
                            root_0 = (Object)adaptor.becomeRoot(string_literal40_tree, root_0);


                            }
                            break;
                        case 2 :
                            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:81:24: '!='
                            {
                            string_literal41=(Token)match(input,22,FOLLOW_22_in_condExpr507); 
                            string_literal41_tree = (Object)adaptor.create(string_literal41);
                            root_0 = (Object)adaptor.becomeRoot(string_literal41_tree, root_0);


                            }
                            break;

                    }

                    pushFollow(FOLLOW_aexpr_in_condExpr511);
                    aexpr42=aexpr();

                    state._fsp--;

                    adaptor.addChild(root_0, aexpr42.getTree());

                    }
                    break;

            }


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
    // $ANTLR end "condExpr"

    public static class aexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "aexpr"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:84:1: aexpr : mexpr ( '+' mexpr )* ;
    public final CMinusParser.aexpr_return aexpr() throws RecognitionException {
        CMinusParser.aexpr_return retval = new CMinusParser.aexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal44=null;
        CMinusParser.mexpr_return mexpr43 = null;

        CMinusParser.mexpr_return mexpr45 = null;


        Object char_literal44_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:85:5: ( mexpr ( '+' mexpr )* )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:85:9: mexpr ( '+' mexpr )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_mexpr_in_aexpr533);
            mexpr43=mexpr();

            state._fsp--;

            adaptor.addChild(root_0, mexpr43.getTree());
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:85:15: ( '+' mexpr )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==23) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:85:16: '+' mexpr
            	    {
            	    char_literal44=(Token)match(input,23,FOLLOW_23_in_aexpr536); 
            	    char_literal44_tree = (Object)adaptor.create(char_literal44);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal44_tree, root_0);

            	    pushFollow(FOLLOW_mexpr_in_aexpr539);
            	    mexpr45=mexpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, mexpr45.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // $ANTLR end "aexpr"

    public static class mexpr_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mexpr"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:88:1: mexpr : atom ( '*' atom )* ;
    public final CMinusParser.mexpr_return mexpr() throws RecognitionException {
        CMinusParser.mexpr_return retval = new CMinusParser.mexpr_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token char_literal47=null;
        CMinusParser.atom_return atom46 = null;

        CMinusParser.atom_return atom48 = null;


        Object char_literal47_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:89:5: ( atom ( '*' atom )* )
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:89:9: atom ( '*' atom )*
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_atom_in_mexpr560);
            atom46=atom();

            state._fsp--;

            adaptor.addChild(root_0, atom46.getTree());
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:89:14: ( '*' atom )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==24) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:89:15: '*' atom
            	    {
            	    char_literal47=(Token)match(input,24,FOLLOW_24_in_mexpr563); 
            	    char_literal47_tree = (Object)adaptor.create(char_literal47);
            	    root_0 = (Object)adaptor.becomeRoot(char_literal47_tree, root_0);

            	    pushFollow(FOLLOW_atom_in_mexpr566);
            	    atom48=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom48.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // $ANTLR end "mexpr"

    public static class atom_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:92:1: atom : ( ID | INT | '(' expr ')' -> expr );
    public final CMinusParser.atom_return atom() throws RecognitionException {
        CMinusParser.atom_return retval = new CMinusParser.atom_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID49=null;
        Token INT50=null;
        Token char_literal51=null;
        Token char_literal53=null;
        CMinusParser.expr_return expr52 = null;


        Object ID49_tree=null;
        Object INT50_tree=null;
        Object char_literal51_tree=null;
        Object char_literal53_tree=null;
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleTokenStream stream_14=new RewriteRuleTokenStream(adaptor,"token 14");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:92:5: ( ID | INT | '(' expr ')' -> expr )
            int alt12=3;
            switch ( input.LA(1) ) {
            case ID:
                {
                alt12=1;
                }
                break;
            case INT:
                {
                alt12=2;
                }
                break;
            case 14:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:92:9: ID
                    {
                    root_0 = (Object)adaptor.nil();

                    ID49=(Token)match(input,ID,FOLLOW_ID_in_atom582); 
                    ID49_tree = (Object)adaptor.create(ID49);
                    adaptor.addChild(root_0, ID49_tree);


                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:93:9: INT
                    {
                    root_0 = (Object)adaptor.nil();

                    INT50=(Token)match(input,INT,FOLLOW_INT_in_atom592); 
                    INT50_tree = (Object)adaptor.create(INT50);
                    adaptor.addChild(root_0, INT50_tree);


                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/CMinus.g:94:9: '(' expr ')'
                    {
                    char_literal51=(Token)match(input,14,FOLLOW_14_in_atom602);  
                    stream_14.add(char_literal51);

                    pushFollow(FOLLOW_expr_in_atom604);
                    expr52=expr();

                    state._fsp--;

                    stream_expr.add(expr52.getTree());
                    char_literal53=(Token)match(input,16,FOLLOW_16_in_atom606);  
                    stream_16.add(char_literal53);



                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 94:22: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;

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
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_declaration_in_program65 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_variable_in_declaration85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_declaration95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_variable114 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_variable116 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_variable118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_type0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_function175 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_function177 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_function187 = new BitSet(new long[]{0x0000000000013000L});
    public static final BitSet FOLLOW_formalParameter_in_function191 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_15_in_function194 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_formalParameter_in_function196 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_16_in_function204 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_block_in_function214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_formalParameter256 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_ID_in_formalParameter258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_block292 = new BitSet(new long[]{0x00000000000E7B00L});
    public static final BitSet FOLLOW_variable_in_block294 = new BitSet(new long[]{0x00000000000E7B00L});
    public static final BitSet FOLLOW_stat_in_block297 = new BitSet(new long[]{0x00000000000E4B00L});
    public static final BitSet FOLLOW_18_in_block300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forStat_in_stat333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_stat341 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_stat343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignStat_in_stat360 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_stat362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_stat371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_forStat391 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_forStat393 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_assignStat_in_forStat397 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_forStat399 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_expr_in_forStat401 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_forStat403 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_assignStat_in_forStat407 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_forStat409 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_block_in_forStat411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_assignStat454 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_assignStat456 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_expr_in_assignStat458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExpr_in_expr484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aexpr_in_condExpr499 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_21_in_condExpr504 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_22_in_condExpr507 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_aexpr_in_condExpr511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mexpr_in_aexpr533 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_23_in_aexpr536 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_mexpr_in_aexpr539 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_atom_in_mexpr560 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_24_in_mexpr563 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_atom_in_mexpr566 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_ID_in_atom582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atom592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_atom602 = new BitSet(new long[]{0x0000000000004300L});
    public static final BitSet FOLLOW_expr_in_atom604 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_16_in_atom606 = new BitSet(new long[]{0x0000000000000002L});

}