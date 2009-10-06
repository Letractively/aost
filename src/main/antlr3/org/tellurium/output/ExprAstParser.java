// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g 2009-09-15 08:33:42

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class ExprAstParser extends Parser {
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


        public ExprAstParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ExprAstParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return ExprAstParser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g"; }


    public static class prog_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:8:1: prog : ( stat )+ ;
    public final ExprAstParser.prog_return prog() throws RecognitionException {
        ExprAstParser.prog_return retval = new ExprAstParser.prog_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        ExprAstParser.stat_return stat1 = null;



        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:14:5: ( ( stat )+ )
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:14:9: ( stat )+
            {
            root_0 = (CommonTree)adaptor.nil();

            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:14:9: ( stat )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=NEWLINE && LA1_0<=INT)||LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:14:11: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_prog39);
            	    stat1=stat();

            	    state._fsp--;

            	    adaptor.addChild(root_0, stat1.getTree());
            	    System.out.println(
            	                    (stat1!=null?((CommonTree)stat1.tree):null)==null?"null":(stat1!=null?((CommonTree)stat1.tree):null).toStringTree());

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

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "prog"

    public static class stat_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:17:1: stat : ( expr NEWLINE -> expr | ID '=' expr NEWLINE -> ^( '=' ID expr ) | NEWLINE ->);
    public final ExprAstParser.stat_return stat() throws RecognitionException {
        ExprAstParser.stat_return retval = new ExprAstParser.stat_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NEWLINE3=null;
        Token ID4=null;
        Token char_literal5=null;
        Token NEWLINE7=null;
        Token NEWLINE8=null;
        ExprAstParser.expr_return expr2 = null;

        ExprAstParser.expr_return expr6 = null;


        CommonTree NEWLINE3_tree=null;
        CommonTree ID4_tree=null;
        CommonTree char_literal5_tree=null;
        CommonTree NEWLINE7_tree=null;
        CommonTree NEWLINE8_tree=null;
        RewriteRuleTokenStream stream_NEWLINE=new RewriteRuleTokenStream(adaptor,"token NEWLINE");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_8=new RewriteRuleTokenStream(adaptor,"token 8");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:17:5: ( expr NEWLINE -> expr | ID '=' expr NEWLINE -> ^( '=' ID expr ) | NEWLINE ->)
            int alt2=3;
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

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:17:9: expr NEWLINE
                    {
                    pushFollow(FOLLOW_expr_in_stat54);
                    expr2=expr();

                    state._fsp--;

                    stream_expr.add(expr2.getTree());
                    NEWLINE3=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat56);  
                    stream_NEWLINE.add(NEWLINE3);



                    // AST REWRITE
                    // elements: expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 17:29: -> expr
                    {
                        adaptor.addChild(root_0, stream_expr.nextTree());

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:18:9: ID '=' expr NEWLINE
                    {
                    ID4=(Token)match(input,ID,FOLLOW_ID_in_stat77);  
                    stream_ID.add(ID4);

                    char_literal5=(Token)match(input,8,FOLLOW_8_in_stat79);  
                    stream_8.add(char_literal5);

                    pushFollow(FOLLOW_expr_in_stat81);
                    expr6=expr();

                    state._fsp--;

                    stream_expr.add(expr6.getTree());
                    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat83);  
                    stream_NEWLINE.add(NEWLINE7);



                    // AST REWRITE
                    // elements: ID, 8, expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 18:29: -> ^( '=' ID expr )
                    {
                        // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:18:32: ^( '=' ID expr )
                        {
                        CommonTree root_1 = (CommonTree)adaptor.nil();
                        root_1 = (CommonTree)adaptor.becomeRoot(stream_8.nextNode(), root_1);

                        adaptor.addChild(root_1, stream_ID.nextNode());
                        adaptor.addChild(root_1, stream_expr.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:19:9: NEWLINE
                    {
                    NEWLINE8=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_stat103);  
                    stream_NEWLINE.add(NEWLINE8);



                    // AST REWRITE
                    // elements: 
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 19:29: ->
                    {
                        root_0 = null;
                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stat"

    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:1: expr : multExpr ( ( '+' | '-' ) multExpr )* ;
    public final ExprAstParser.expr_return expr() throws RecognitionException {
        ExprAstParser.expr_return retval = new ExprAstParser.expr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal10=null;
        Token char_literal11=null;
        ExprAstParser.multExpr_return multExpr9 = null;

        ExprAstParser.multExpr_return multExpr12 = null;


        CommonTree char_literal10_tree=null;
        CommonTree char_literal11_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:5: ( multExpr ( ( '+' | '-' ) multExpr )* )
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:9: multExpr ( ( '+' | '-' ) multExpr )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_multExpr_in_expr133);
            multExpr9=multExpr();

            state._fsp--;

            adaptor.addChild(root_0, multExpr9.getTree());
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:18: ( ( '+' | '-' ) multExpr )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>=9 && LA4_0<=10)) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:19: ( '+' | '-' ) multExpr
            	    {
            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:19: ( '+' | '-' )
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0==9) ) {
            	        alt3=1;
            	    }
            	    else if ( (LA3_0==10) ) {
            	        alt3=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 3, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:20: '+'
            	            {
            	            char_literal10=(Token)match(input,9,FOLLOW_9_in_expr137); 
            	            char_literal10_tree = (CommonTree)adaptor.create(char_literal10);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal10_tree, root_0);


            	            }
            	            break;
            	        case 2 :
            	            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:24:25: '-'
            	            {
            	            char_literal11=(Token)match(input,10,FOLLOW_10_in_expr140); 
            	            char_literal11_tree = (CommonTree)adaptor.create(char_literal11);
            	            root_0 = (CommonTree)adaptor.becomeRoot(char_literal11_tree, root_0);


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_multExpr_in_expr144);
            	    multExpr12=multExpr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, multExpr12.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class multExpr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multExpr"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:27:1: multExpr : atom ( '*' atom )* ;
    public final ExprAstParser.multExpr_return multExpr() throws RecognitionException {
        ExprAstParser.multExpr_return retval = new ExprAstParser.multExpr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token char_literal14=null;
        ExprAstParser.atom_return atom13 = null;

        ExprAstParser.atom_return atom15 = null;


        CommonTree char_literal14_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:28:5: ( atom ( '*' atom )* )
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:28:9: atom ( '*' atom )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_atom_in_multExpr166);
            atom13=atom();

            state._fsp--;

            adaptor.addChild(root_0, atom13.getTree());
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:28:14: ( '*' atom )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==11) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:28:15: '*' atom
            	    {
            	    char_literal14=(Token)match(input,11,FOLLOW_11_in_multExpr169); 
            	    char_literal14_tree = (CommonTree)adaptor.create(char_literal14);
            	    root_0 = (CommonTree)adaptor.becomeRoot(char_literal14_tree, root_0);

            	    pushFollow(FOLLOW_atom_in_multExpr172);
            	    atom15=atom();

            	    state._fsp--;

            	    adaptor.addChild(root_0, atom15.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "multExpr"

    public static class atom_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:31:1: atom : ( INT | ID | '(' expr ')' );
    public final ExprAstParser.atom_return atom() throws RecognitionException {
        ExprAstParser.atom_return retval = new ExprAstParser.atom_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token INT16=null;
        Token ID17=null;
        Token char_literal18=null;
        Token char_literal20=null;
        ExprAstParser.expr_return expr19 = null;


        CommonTree INT16_tree=null;
        CommonTree ID17_tree=null;
        CommonTree char_literal18_tree=null;
        CommonTree char_literal20_tree=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:31:5: ( INT | ID | '(' expr ')' )
            int alt6=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt6=1;
                }
                break;
            case ID:
                {
                alt6=2;
                }
                break;
            case 12:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:31:9: INT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    INT16=(Token)match(input,INT,FOLLOW_INT_in_atom189); 
                    INT16_tree = (CommonTree)adaptor.create(INT16);
                    adaptor.addChild(root_0, INT16_tree);


                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:32:9: ID
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    ID17=(Token)match(input,ID,FOLLOW_ID_in_atom200); 
                    ID17_tree = (CommonTree)adaptor.create(ID17);
                    adaptor.addChild(root_0, ID17_tree);


                    }
                    break;
                case 3 :
                    // /opt/workspace/test/parser/src/main/antlr3/org/tellurium/ExprAst.g:33:9: '(' expr ')'
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    char_literal18=(Token)match(input,12,FOLLOW_12_in_atom210); 
                    pushFollow(FOLLOW_expr_in_atom213);
                    expr19=expr();

                    state._fsp--;

                    adaptor.addChild(root_0, expr19.getTree());
                    char_literal20=(Token)match(input,13,FOLLOW_13_in_atom215); 

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    // Delegated rules


 

    public static final BitSet FOLLOW_stat_in_prog39 = new BitSet(new long[]{0x0000000000001072L});
    public static final BitSet FOLLOW_expr_in_stat54 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat56 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat77 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_stat79 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_expr_in_stat81 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_NEWLINE_in_stat83 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_stat103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multExpr_in_expr133 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_9_in_expr137 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_10_in_expr140 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_multExpr_in_expr144 = new BitSet(new long[]{0x0000000000000602L});
    public static final BitSet FOLLOW_atom_in_multExpr166 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_11_in_multExpr169 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_atom_in_multExpr172 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_INT_in_atom189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_atom200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_atom210 = new BitSet(new long[]{0x0000000000001060L});
    public static final BitSet FOLLOW_expr_in_atom213 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_atom215 = new BitSet(new long[]{0x0000000000000002L});

}