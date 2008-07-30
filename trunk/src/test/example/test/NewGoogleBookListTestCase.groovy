package example.test

import example.google.NewGoogleBooksList
import org.tellurium.test.StandaloneTelluriumTestCase
import org.tellurium.object.UiObject
import org.tellurium.object.UrlLink

/**
 *   Sample test cases for Google book list page at:
 *
 *        http://books.google.com/
 *   to demostrate the List UI object, composite locator, and group feature
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class NewGoogleBookListTestCase extends StandaloneTelluriumTestCase {

    protected NewGoogleBooksList ngbl

    public void initUi() {
        ngbl = new NewGoogleBooksList()
        ngbl.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testBookCategory(){
        connectUrl("http://books.google.com/")
        String category = ngbl.getCategory()
        assertEquals("Fiction", category)

        int size = ngbl.getListSize()

        assertEquals(8, size)

        List lst = ngbl.getAllObjectInList()

        assertNotNull(lst)

        int index
        String search =  "Poetry"
        int i = 1
        for(UiObject obj : lst){
            assertTrue(obj instanceof UrlLink)
            if(search.equals(ngbl.getText(i))){
                index = i
                break
            }
            i++
        }

        ngbl.clickList(index)
    }


}