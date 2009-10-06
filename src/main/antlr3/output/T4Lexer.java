// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/T4.g 2009-09-15 22:06:31

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class T4Lexer extends Lexer {
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

    public T4Lexer() {;} 
    public T4Lexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public T4Lexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/T4.g"; }

    // $ANTLR start "T__7"
    public final void mT__7() throws RecognitionException {
        try {
            int _type = T__7;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:3:6: ( '{' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:3:8: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__7"

    // $ANTLR start "T__8"
    public final void mT__8() throws RecognitionException {
        try {
            int _type = T__8;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:4:6: ( '}' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:4:8: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__8"

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:5:6: ( 'int' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:5:8: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:6:7: ( ';' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:6:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:7:7: ( '=' )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:7:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:37:5: ( ( 'a' .. 'z' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:37:9: ( 'a' .. 'z' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:37:9: ( 'a' .. 'z' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T4.g:37:9: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:38:5: ( ( '0' .. '9' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:38:9: ( '0' .. '9' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:38:9: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T4.g:38:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:39:5: ( ( ' ' | '\\n' | '\\r' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:39:9: ( ' ' | '\\n' | '\\r' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/T4.g:39:9: ( ' ' | '\\n' | '\\r' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0=='\n'||LA3_0=='\r'||LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/T4.g:
            	    {
            	    if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:8: ( T__7 | T__8 | T__9 | T__10 | T__11 | ID | INT | WS )
        int alt4=8;
        alt4 = dfa4.predict(input);
        switch (alt4) {
            case 1 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:10: T__7
                {
                mT__7(); 

                }
                break;
            case 2 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:15: T__8
                {
                mT__8(); 

                }
                break;
            case 3 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:20: T__9
                {
                mT__9(); 

                }
                break;
            case 4 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:25: T__10
                {
                mT__10(); 

                }
                break;
            case 5 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:31: T__11
                {
                mT__11(); 

                }
                break;
            case 6 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:37: ID
                {
                mID(); 

                }
                break;
            case 7 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:40: INT
                {
                mINT(); 

                }
                break;
            case 8 :
                // /opt/workspace/test/parser/src/main/antlr3/T4.g:1:44: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\3\uffff\1\6\5\uffff\1\6\1\13\1\uffff";
    static final String DFA4_eofS =
        "\14\uffff";
    static final String DFA4_minS =
        "\1\12\2\uffff\1\156\5\uffff\1\164\1\141\1\uffff";
    static final String DFA4_maxS =
        "\1\175\2\uffff\1\156\5\uffff\1\164\1\172\1\uffff";
    static final String DFA4_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\1\5\1\6\1\7\1\10\2\uffff\1\3";
    static final String DFA4_specialS =
        "\14\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\10\2\uffff\1\10\22\uffff\1\10\17\uffff\12\7\1\uffff\1\4\1"+
            "\uffff\1\5\43\uffff\10\6\1\3\21\6\1\1\1\uffff\1\2",
            "",
            "",
            "\1\11",
            "",
            "",
            "",
            "",
            "",
            "\1\12",
            "\32\6",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__7 | T__8 | T__9 | T__10 | T__11 | ID | INT | WS );";
        }
    }
 

}