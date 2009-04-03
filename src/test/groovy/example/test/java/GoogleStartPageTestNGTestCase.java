package example.test.java;

import example.google.NewGoogleStartPage;
import org.tellurium.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test NG test case
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2008
 */
public class GoogleStartPageTestNGTestCase extends TelluriumTestNGTestCase {
    static{
        setCustomConfig(true, 5555, "*chrome", true, null, "localhost");
    }
    
    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
    }

    @BeforeMethod
    public void connectToGoogle(){
       connectUrl("http://www.google.com");
    }

    @Test
    public void testGoogleSearch(){
        ngsp.doGoogleSearch("tellurium selenium Groovy Test");
   }

   @Test
   public void testGoogleSearchFeelingLucky(){
        ngsp.doImFeelingLucky("tellurium selenium DSL Testing");
   }
}
