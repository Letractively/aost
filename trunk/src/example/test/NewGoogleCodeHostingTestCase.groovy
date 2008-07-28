package example.test

import org.aost.test.StandaloneAostSeleneseTestCase
import example.google.NewGoogleCodeHosting
import org.aost.object.UrlLink

/**
 *    Sample test cases for Google Code host page at:
 *
 *       http://code.google.com/hosting/
 *   to demostrate nested Tables, composite locator, and group feature
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class NewGoogleCodeHostingTestCase extends StandaloneAostSeleneseTestCase {

    protected static NewGoogleCodeHosting ngch

    public void initUi() {
        ngch = new NewGoogleCodeHosting()
        ngch.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testCodeLabelTable(){
        connectUrl("http://code.google.com/hosting/")
        String label = ngch.getModuleLabel()
        assertEquals("Example project labels:", label)

        int nrow = ngch.getLabelTableRowNum()
        assertEquals(3, nrow)

        int ncolumn = ngch.getLabelTableColumnNum()
        assertEquals(6, ncolumn)

        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
               def obj = ngch.getTableElement(i, j)
               assertTrue(obj instanceof UrlLink)
            }
        }
        Map map = ngch.getAllLabels()
        assertEquals(18, map.size())

        int[] index = map.get("Java")
        assertNotNull(index)
        assertEquals(2, index.length)

        //find the url link
        String url = ngch.getUrlLink(index[0], index[1])
        assertEquals("search?q=label%3aJava", url)

        //click on "Java" link
        ngch.clickOnLable(index[0], index[1])
    }
}