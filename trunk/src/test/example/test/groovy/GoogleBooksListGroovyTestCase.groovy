package example.test.groovy

import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import example.google.GoogleBooksList
import org.tellurium.object.UiObject
import org.tellurium.object.UrlLink
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase

/**
 * 
 * Sample test cases for Google book list page at:
 *
 *        http://books.google.com/
 *   to demostrate the List UI object
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class GoogleBooksListGroovyTestCase extends StandaloneTelluriumGroovyTestCase {

    protected GoogleBooksList gbl

    public void initUi() {
        gbl = new GoogleBooksList()
        gbl.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testBookCategory(){
        connectUrl("http://books.google.com/")
        String category = gbl.getCategory()
        assertEquals("Fiction", category)

        int size = gbl.getListSize()
        
        assertEquals(8, size)

        List lst = gbl.getAllObjectInList()

        assertNotNull(lst)

        int index
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