package test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tellurium.test.java.TelluriumTestNGTestCase;

import module.GoogleSearchModule;


/**
 *    Test cases created based on the GoogleSearchModule UI module
 *
 *
 */
public class GoogleSearchTestCase extends TelluriumTestNGTestCase{
    private static GoogleSearchModule gsm;

    @BeforeClass
    public static void initUi() {
        gsm = new GoogleSearchModule();
        gsm.defineUi();
    }

    @BeforeMethod
    public void connectToGoogle(){
       connectUrl("http://www.google.com");
    }

    @Test
    public void testGoogleSearch(){
        gsm.doGoogleSearch("tellurium Groovy Test");
   }

   @Test
   public void testGoogleSearchFeelingLucky(){
        gsm.doImFeelingLucky("tellurium automated Testing");
   }
}
