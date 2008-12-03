package example.test.java;

import example.google.NewGoogleStartPage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tellurium.test.java.TelluriumJavaTestCase;

/**
 * Use Junit 4 directly for Tellurium test
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
public class GoogleStartPageJavaTestCase extends TelluriumJavaTestCase {

    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
    }

    @Test
    public void testGoogleSearch() {
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
    }

    @Test
    public void testGoogleSearchFeelingLucky() {
        connectUrl("http://www.google.com");
        ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
    }

    @Test
    public void testSpecialCharacters() {
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("tellurium.selenium.Groovy @Test) !#$%^& (");
    }

    //test the dynamically added event "click"
    @Test
    public void testClick(){
        connectUrl("http://www.google.com");
        ngsp.testClick();
    }

}
