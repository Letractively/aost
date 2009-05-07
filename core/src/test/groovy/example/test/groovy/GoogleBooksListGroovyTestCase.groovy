package example.test.groovy

import example.google.GoogleBooksList
import example.google.NewGoogleBooksList
import org.tellurium.object.UiObject
import org.tellurium.object.UrlLink
import org.tellurium.test.groovy.TelluriumGroovyTestCase

/**
 * 
 * Sample test cases for Google book list page at:
 *
 *        http://books.google.com/
 *   to demostrate the List UI object
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class GoogleBooksListGroovyTestCase extends TelluriumGroovyTestCase {

    public void initUi() {
    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testBookCategory(){
        //test google book list using Selenium way, i.e., absolute xpath
        GoogleBooksList gbl = new GoogleBooksList()
        gbl.defineUi()
        bookListTest(gbl)

        //test google book list using composite locators
        NewGoogleBooksList ngbl = new NewGoogleBooksList()
        ngbl.defineUi()
        bookListTest(ngbl)
    }

    void testGetListSize(){
        NewGoogleBooksList ngbl = new NewGoogleBooksList()
        ngbl.defineUi()
        connectUrl("http://books.google.com/")
        ngbl.disableJQuerySelector()
        int xsize = ngbl.getBookListSize()
        ngbl.useJQuerySelector()
        int jsize = ngbl.getBookListSize()
        assertEquals(jsize, xsize)
    }

    void bookListTest(gbl){
        connectUrl("http://books.google.com/")
        String category = gbl.getCategory()
        assertEquals("Fiction", category)

        int size = gbl.getBookListSize()

        assertEquals(8, size)

        List lst = gbl.getAllObjectInList()

        assertNotNull(lst)

        int index = -1
        String search =  "Poetry"
        int i = 1
        for(UiObject obj : lst){
            assertTrue(obj instanceof UrlLink)
            if(search.equals(gbl.getText(i))){
                index = i
                break
            }
            i++
        }

        gbl.clickList(index)
    }
}