// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/List.g 2009-09-15 23:40:40

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class ListLexer extends Lexer {
    public static final int INT=5;
    public static final int WS=6;
    public static final int ID=4;
    public static final int EOF=-1;

    // delegates
    // delegators

    public ListLexer() {;} 
    public ListLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ListLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/List.g"; }

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/List.g:4:4: ( ( 'a' .. 'z' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/List.g:4:6: ( 'a' .. 'z' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/List.g:4:6: ( 'a' .. 'z' )+
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
            	    // /opt/workspace/test/parser/src/main/antlr3/List.g:4:6: 'a' .. 'z'
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
            // /opt/workspace/test/parser/src/main/antlr3/List.g:5:5: ( ( '0' .. '9' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/List.g:5:7: ( '0' .. '9' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/List.g:5:7: ( '0' .. '9' )+
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
            	    // /opt/workspace/test/parser/src/main/antlr3/List.g:5:7: '0' .. '9'
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
            // /opt/workspace/test/parser/src/main/antlr3/List.g:6:4: ( ( ' ' | '\\n' | '\\r' ) )
            // /opt/workspace/test/parser/src/main/antlr3/List.g:6:6: ( ' ' | '\\n' | '\\r' )
            {
            if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /opt/workspace/test/parser/src/main/antlr3/List.g:1:8: ( ID | INT | WS )
        int alt3=3;
        switch ( input.LA(1) ) {
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
        case 'g':
        case 'h':
        case 'i':
        case 'j':
        case 'k':
        case 'l':
        case 'm':
        case 'n':
        case 'o':
        case 'p':
        case 'q':
        case 'r':
        case 's':
        case 't':
        case 'u':
        case 'v':
        case 'w':
        case 'x':
        case 'y':
        case 'z':
            {
            alt3=1;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt3=2;
            }
            break;
        case '\n':
        case '\r':
        case ' ':
            {
            alt3=3;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 3, 0, input);

            throw nvae;
        }

        switch (alt3) {
            case 1 :
                // /opt/workspace/test/parser/src/main/antlr3/List.g:1:10: ID
                {
                mID(); 

                }
                break;
            case 2 :
                // /opt/workspace/test/parser/src/main/antlr3/List.g:1:13: INT
                {
                mINT(); 

                }
                break;
            case 3 :
                // /opt/workspace/test/parser/src/main/antlr3/List.g:1:17: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}