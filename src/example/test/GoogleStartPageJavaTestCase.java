package example.test;

import aost.test.AostJavaTestCase;
import example.google.NewGoogleStartPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 * Use Junit 4 directly for AOST test
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
public class GoogleStartPageJavaTestCase extends AostJavaTestCase {

    protected static NewGoogleStartPage ngsp;

    @BeforeClass
    public static void initUi() {
        ngsp = new NewGoogleStartPage();
        ngsp.defineUi();
    }

    @Test
    public void testGoogleSearch(){
        connectUrl("http://www.google.com");
        ngsp.doGoogleSearch("aost selenium");
   }

   @Test
   public void testGoogleSearchFeelingLucky(){
        connectUrl("http://www.google.com");
        ngsp.doImFeelingLucky("aost selenium");
   }
}
