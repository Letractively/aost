package example.test.java;

import example.google.NewGoogleBooksList;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tellurium.test.java.TelluriumJavaTestCase;

/**
 * Java version of Google Books List tests
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 2, 2008
 */
public class GoogleBooksListJavaTestCase extends TelluriumJavaTestCase {
    private static NewGoogleBooksList ngbl;

    @BeforeClass
    public static void initUi() {
        ngbl = new NewGoogleBooksList();
        ngbl.defineUi();
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
