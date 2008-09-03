package example.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import example.google.NewGoogleBooksList;
import static org.junit.Assert.*;

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

        int size = ngbl.getListSize();

        assertEquals(8, size);           
    }
}
