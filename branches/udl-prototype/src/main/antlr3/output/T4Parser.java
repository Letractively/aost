// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/T4.g 2009-09-15 22:06:31

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class T4Parser extends Parser {
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


        public T4Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public T4Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return T4Parser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/T4.g"; }



    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/T4.g:3:1: prog : block ;
    public final void prog() throws RecognitionException {
        try {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:3:5: ( block )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:3:9: block
            {
            pushFollow(FOLLOW_block_in_prog11);
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
        /** List of symbols defined within this block */
            List symbols;
    }
    protected Stack block_stack = new Stack();


    // $ANTLR start "block"
    // /opt/workspace/test/parser/src/main/antlr3/T4.g:6:1: block : '{' ( decl )* ( stat )+ '}' ;
    public final void block() throws RecognitionException {
        block_stack.push(new block_scope());

            // initialize symbol list
            ((block_scope)block_stack.peek()).symbols = new ArrayList();

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:5: ( '{' ( decl )* ( stat )+ '}' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:9: '{' ( decl )* ( stat )+ '}'
            {
            match(input,7,FOLLOW_7_in_block39); 
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:13: ( decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==9) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:13: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_block41);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:19: ( stat )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==ID) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T4.g:15:19: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_block44);
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

            match(input,8,FOLLOW_8_in_block47); 
            System.out.println("symbols="+((block_scope)block_stack.peek()).symbols);

            }

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
    // /opt/workspace/test/parser/src/main/antlr3/T4.g:21:1: decl : 'int' ID ';' ;
    public final void decl() throws RecognitionException {
        Token ID1=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:22:5: ( 'int' ID ';' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:22:9: 'int' ID ';'
            {
            match(input,9,FOLLOW_9_in_decl91); 
            ID1=(Token)match(input,ID,FOLLOW_ID_in_decl93); 
            ((block_scope)block_stack.peek()).symbols.add((ID1!=null?ID1.getText():null));
            match(input,10,FOLLOW_10_in_decl97); 

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
    // /opt/workspace/test/parser/src/main/antlr3/T4.g:25:1: stat : ID '=' INT ';' ;
    public final void stat() throws RecognitionException {
        Token ID2=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:30:5: ( ID '=' INT ';' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:30:9: ID '=' INT ';'
            {
            ID2=(Token)match(input,ID,FOLLOW_ID_in_stat113); 
            match(input,11,FOLLOW_11_in_stat115); 
            match(input,INT,FOLLOW_INT_in_stat117); 
            match(input,10,FOLLOW_10_in_stat119); 

                    if ( !((block_scope)block_stack.peek()).symbols.contains((ID2!=null?ID2.getText():null)) ) {
                        System.err.println("undefined variable: "+(ID2!=null?ID2.getText():null));
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
    // $ANTLR end "stat"

    // Delegated rules


 

    public static final BitSet FOLLOW_block_in_prog11 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_7_in_block39 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_decl_in_block41 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_stat_in_block44 = new BitSet(new long[]{0x0000000000000310L});
    public static final BitSet FOLLOW_8_in_block47 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_decl91 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_decl93 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_decl97 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat113 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_11_in_stat115 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_stat117 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_stat119 = new BitSet(new long[]{0x0000000000000002L});

}