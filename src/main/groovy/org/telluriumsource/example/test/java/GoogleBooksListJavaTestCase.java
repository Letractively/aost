package org.telluriumsource.example.test.java;

import org.telluriumsource.example.google.NewGoogleBooksList;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * Java version of Google Books List tests
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 2, 2008
 */
public class GoogleBooksListJavaTestCase extends TelluriumJUnitTestCase {
    private static NewGoogleBooksList ngbl;

    @BeforeClass
    public static void initUi() {
        ngbl = new NewGoogleBooksList();
        ngbl.defineUi();
        connectSeleniumServer();
        useTrace(true);
        useCache(true);
    }

    @Test
    public void testBookCategory(){
        connectUrl("http://books.google.com/");
        String category = ngbl.getCategory();
        assertEquals("Fiction", category);

        int size = ngbl.getBookListSize();

        assertEquals(8, size);           
    }

   @Test
    public void testNewBookCategory(){
        connectUrl("http://books.google.com/");
        String category = ngbl.getCategory();
        assertEquals("Fiction", category);
        ngbl.clickNewList(1);           
    }

}
