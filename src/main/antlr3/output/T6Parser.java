// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/T6.g 2009-09-15 22:25:47

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class T6Parser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "INT", "WS", "'void'", "'('", "')'", "'{'", "'}'", "'int'", "';'", "'='"
    };
    public static final int WS=6;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;
    public static final int INT=5;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__8=8;
    public static final int T__7=7;

    // delegates
    // delegators

    protected static class CScope_scope {
        String name;
        List symbols;
    }
    protected Stack CScope_stack = new Stack();


        public T6Parser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public T6Parser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return T6Parser.tokenNames; }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/T6.g"; }


    /** Is id defined in a CScope?  Walk from top of stack
     *  downwards looking for a symbols list containing id.
     */
    boolean isDefined(String id) {
        for (int s=CScope_stack.size()-1; s>=0; s--) {
            if ( ((CScope_scope)CScope_stack.elementAt(s)).symbols.contains(id) ) {
                System.out.println(id+" found in "+((CScope_scope)CScope_stack.elementAt(s)).name);
                return true;
            }
        }
        return false;
    }



    // $ANTLR start "prog"
    // /opt/workspace/test/parser/src/main/antlr3/T6.g:33:1: prog : ( decl )* ( func )* ;
    public final void prog() throws RecognitionException {
        CScope_stack.push(new CScope_scope());


            // initialize a scope for overall C program
            ((CScope_scope)CScope_stack.peek()).symbols = new ArrayList();
            ((CScope_scope)CScope_stack.peek()).name = "global";

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:5: ( ( decl )* ( func )* )
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:9: ( decl )* ( func )*
            {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:9: ( decl )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:9: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_prog54);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:15: ( func )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==7) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:44:15: func
            	    {
            	    pushFollow(FOLLOW_func_in_prog57);
            	    func();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            }


                // dump global symbols after matching entire program
                System.out.println("global symbols = "+((CScope_scope)CScope_stack.peek()).symbols);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            CScope_stack.pop();

        }
        return ;
    }
    // $ANTLR end "prog"


    // $ANTLR start "func"
    // /opt/workspace/test/parser/src/main/antlr3/T6.g:47:1: func : 'void' ID '(' ')' '{' ( decl )* ( stat )+ '}' ;
    public final void func() throws RecognitionException {
        CScope_stack.push(new CScope_scope());

        Token ID1=null;


            // initialize a scope for this function
            ((CScope_scope)CScope_stack.peek()).symbols = new ArrayList();

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:5: ( 'void' ID '(' ')' '{' ( decl )* ( stat )+ '}' )
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:9: 'void' ID '(' ')' '{' ( decl )* ( stat )+ '}'
            {
            match(input,7,FOLLOW_7_in_func93); 
            ID1=(Token)match(input,ID,FOLLOW_ID_in_func95); 
            ((CScope_scope)CScope_stack.peek()).name =(ID1!=null?ID1.getText():null);
            match(input,8,FOLLOW_8_in_func99); 
            match(input,9,FOLLOW_9_in_func101); 
            match(input,10,FOLLOW_10_in_func103); 
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:57: ( decl )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==12) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:57: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_func105);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:63: ( stat )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==ID||LA4_0==10) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:59:63: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_func108);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            match(input,11,FOLLOW_11_in_func111); 

            }


                // dump variables defined within the function itself
                System.out.println("function "+((CScope_scope)CScope_stack.peek()).name+" symbols = "+
                                   ((CScope_scope)CScope_stack.peek()).symbols);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            CScope_stack.pop();

        }
        return ;
    }
    // $ANTLR end "func"


    // $ANTLR start "block"
    // /opt/workspace/test/parser/src/main/antlr3/T6.g:62:1: block : '{' ( decl )* ( stat )+ '}' ;
    public final void block() throws RecognitionException {
        CScope_stack.push(new CScope_scope());


            // initialize a scope for this code block
            ((CScope_scope)CScope_stack.peek()).symbols = new ArrayList();
            ((CScope_scope)CScope_stack.peek()).name = "level "+CScope_stack.size();

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:5: ( '{' ( decl )* ( stat )+ '}' )
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:9: '{' ( decl )* ( stat )+ '}'
            {
            match(input,10,FOLLOW_10_in_block145); 
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:13: ( decl )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==12) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:13: decl
            	    {
            	    pushFollow(FOLLOW_decl_in_block147);
            	    decl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:19: ( stat )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==ID||LA6_0==10) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T6.g:74:19: stat
            	    {
            	    pushFollow(FOLLOW_stat_in_block150);
            	    stat();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            match(input,11,FOLLOW_11_in_block153); 

            }


                // dump variables defined within this code block
                System.out.println("code block level "+CScope_stack.size()+" = "+
                                   ((CScope_scope)CScope_stack.peek()).symbols);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            CScope_stack.pop();

        }
        return ;
    }
    // $ANTLR end "block"


    // $ANTLR start "decl"
    // /opt/workspace/test/parser/src/main/antlr3/T6.g:78:1: decl : 'int' ID ';' ;
    public final void decl() throws RecognitionException {
        Token ID2=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:79:5: ( 'int' ID ';' )
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:79:9: 'int' ID ';'
            {
            match(input,12,FOLLOW_12_in_decl170); 
            ID2=(Token)match(input,ID,FOLLOW_ID_in_decl172); 
            ((CScope_scope)CScope_stack.peek()).symbols.add((ID2!=null?ID2.getText():null));
            match(input,13,FOLLOW_13_in_decl176); 

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
    // /opt/workspace/test/parser/src/main/antlr3/T6.g:82:1: stat : ( ID '=' INT ';' | block );
    public final void stat() throws RecognitionException {
        Token ID3=null;

        try {
            // /opt/workspace/test/parser/src/main/antlr3/T6.g:85:5: ( ID '=' INT ';' | block )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ID) ) {
                alt7=1;
            }
            else if ( (LA7_0==10) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /opt/workspace/test/parser/src/main/antlr3/T6.g:85:9: ID '=' INT ';'
                    {
                    ID3=(Token)match(input,ID,FOLLOW_ID_in_stat192); 
                    match(input,14,FOLLOW_14_in_stat194); 
                    match(input,INT,FOLLOW_INT_in_stat196); 
                    match(input,13,FOLLOW_13_in_stat198); 

                            if ( !isDefined((ID3!=null?ID3.getText():null)) ) {
                                System.err.println("undefined variable level "+
                                    CScope_stack.size()+": "+(ID3!=null?ID3.getText():null));
                            }
                            

                    }
                    break;
                case 2 :
                    // /opt/workspace/test/parser/src/main/antlr3/T6.g:92:9: block
                    {
                    pushFollow(FOLLOW_block_in_stat218);
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


 

    public static final BitSet FOLLOW_decl_in_prog54 = new BitSet(new long[]{0x0000000000001082L});
    public static final BitSet FOLLOW_func_in_prog57 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_7_in_func93 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_func95 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_8_in_func99 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_func101 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_func103 = new BitSet(new long[]{0x0000000000001490L});
    public static final BitSet FOLLOW_decl_in_func105 = new BitSet(new long[]{0x0000000000001490L});
    public static final BitSet FOLLOW_stat_in_func108 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_11_in_func111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_10_in_block145 = new BitSet(new long[]{0x0000000000001490L});
    public static final BitSet FOLLOW_decl_in_block147 = new BitSet(new long[]{0x0000000000001490L});
    public static final BitSet FOLLOW_stat_in_block150 = new BitSet(new long[]{0x0000000000000C10L});
    public static final BitSet FOLLOW_11_in_block153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_decl170 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_decl172 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_decl176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_stat192 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_14_in_stat194 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_stat196 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_13_in_stat198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_block_in_stat218 = new BitSet(new long[]{0x0000000000000002L});

}