// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/T5.g 2009-09-15 22:14:33

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class T5Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "WS", "'{'", "'}'", "'int'", "';'", "'='"
    };
    public static final int WS=6;
    public static final int T__11=11;
    public static final int T__10=10;
    public static final int INT=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;

    // delegates
    // delegators


        public T5Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public T5Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return T5Parser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/T5.g"; }


    /** Track the nesting level for better messages */
    int level=0;



    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/T5.g:8:1: prog : block ;
    public final void prog() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:8:5: ( block )
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:8:9: block
            {
            pushFollow(FOLLOW_block_in_prog17);
            block();

            state._fsp--;


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

    protected static class block_scope {
        List symbols;
    }
    protected Stack block_stack = new Stack();


    // $ANTLR start "block"
    // /opt/workspace/test/parser/src/main/antlr3/T5.g:10:1: block : '{' ( decl )* ( stat )+ '}' ;
    public final void block() throws RecognitionException {
        block_stack.push(new block_scope());

            ((block_scope)block_stack.peek()).symbols = new ArrayList();
            level++;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:5: ( '{' ( decl )* ( stat )+ '}' )
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:9: '{' ( decl )* ( stat )+ '}'
            {
            match(input,7,FOLLOW_7_in_block49); 
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:13: ( decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==9) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:13: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_block51);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:19: ( stat )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ID||LA2_0==7) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T5.g:22:19: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_block54);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);

            match(input,8,FOLLOW_8_in_block57); 

            }


                System.out.println("symbols level "+level+" = "+((block_scope)block_stack.peek()).symbols);
                level--;

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            block_stack.pop();
        }
        return ;
    }
    // $ANTLR end "block"


    // $ANTLR start "decl"
    // /opt/workspace/test/parser/src/main/antlr3/T5.g:24:1: decl : 'int' ID ';' ;
    public final void decl() throws RecognitionException {
        Token ID1=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:24:5: ( 'int' ID ';' )
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:24:9: 'int' ID ';'
            {
            match(input,9,FOLLOW_9_in_decl70); 
            ID1=(Token)match(input,ID,FOLLOW_ID_in_decl72); 
            ((block_scope)block_stack.peek()).symbols.add((ID1!=null?ID1.getText():null));
            match(input,10,FOLLOW_10_in_decl76); 

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
    // $ANTLR end "decl"


    // $ANTLR start "stat"
    // /opt/workspace/test/parser/src/main/antlr3/T5.g:26:1: stat : ( ID '=' INT ';' | block );
    public final void stat() throws RecognitionException {
        Token ID2=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T5.g:26:5: ( ID '=' INT ';' | block )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==ID) ) {
                alt3=1;
            }
            else if ( (LA3_0==7) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/T5.g:26:9: ID '=' INT ';'
                    {
                    ID2=(Token)match(input,ID,FOLLOW_ID_in_stat89); 
                    match(input,11,FOLLOW_11_in_stat91); 
                    match(input,INT,FOLLOW_INT_in_stat93); 
                    match(input,10,FOLLOW_10_in_stat95); 

                            if ( !((block_scope)block_stack.peek()).symbols.contains((ID2!=null?ID2.getText():null)) ) {
                                System.err.println("undefined variable level "+level+
                                                   ": "+(ID2!=null?ID2.getText():null));
                            }
                            

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/T5.g:33:9: block
                    {
                    pushFollow(FOLLOW_block_in_stat115);
                    block();

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

    // Delegated rules


 

    public static final BitSet FOLLOW_block_in_prog17 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_7_in_block49 = new BitSet(new long[]{0x0000000000000290L});
    public static final BitSet FOLLOW_decl_in_block51 = new BitSet(new long[]{0x0000000000000290L});
    public static final BitSet FOLLOW_stat_in_block54 = new BitSet(new long[]{0x0000000000000390L});
    public static final BitSet FOLLOW_8_in_block57 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_decl70 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_decl72 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_decl76 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat89 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_stat91 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_stat93 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_stat95 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat115 = new BitSet(new long[]{0x0000000000000002L});

}