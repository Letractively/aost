package example.test.java;

import org.tellurium.test.java.TelluriumTestNGTestCase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import example.google.NewGoogleStartPage;

/**
 * Test NG test case
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 20, 2008
 */
public class GoogleStartPageTestNGTestCase extends TelluriumTestNGTestCase {

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
