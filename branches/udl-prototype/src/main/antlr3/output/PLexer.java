// $ANTLR 3.1.2 /opt/workspace/test/parser/src/main/antlr3/P.g 2009-09-15 16:34:08

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PLexer extends Lexer {
    public static final int INT=4;
    public static final int WS=5;
    public static final int EOF=-1;
    public static final int T__6=6;

    // delegates
    // delegators

    public PLexer() {;} 
    public PLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/opt/workspace/test/parser/src/main/antlr3/P.g"; }

    // $ANTLR start "T__6"
    public final void mT__6() throws RecognitionException {
        try {
            int _type = T__6;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/P.g:3:6: ( '+' )
            // /opt/workspace/test/parser/src/main/antlr3/P.g:3:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__6"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/P.g:6:5: ( ( '0' .. '9' )+ )
            // /opt/workspace/test/parser/src/main/antlr3/P.g:6:9: ( '0' .. '9' )+
            {
            // /opt/workspace/test/parser/src/main/antlr3/P.g:6:9: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /opt/workspace/test/parser/src/main/antlr3/P.g:6:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /opt/workspace/test/parser/src/main/antlr3/P.g:7:5: ( ' ' | '\\r' | '\\n' )
            // /opt/workspace/test/parser/src/main/antlr3/P.g:
            {
            if ( input.LA(1)=='\n'||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /opt/workspace/test/parser/src/main/antlr3/P.g:1:8: ( T__6 | INT | WS )
        int alt2=3;
        switch ( input.LA(1) ) {
        case '+':
            {
            alt2=1;
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
            alt2=2;
            }
            break;
        case '\n':
        case '\r':
        case ' ':
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
                // /opt/workspace/test/parser/src/main/antlr3/P.g:1:10: T__6
                {
                mT__6(); 

                }
                break;
            case 2 :
                // /opt/workspace/test/parser/src/main/antlr3/P.g:1:15: INT
                {
                mINT(); 

                }
                break;
            case 3 :
                // /opt/workspace/test/parser/src/main/antlr3/P.g:1:19: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}