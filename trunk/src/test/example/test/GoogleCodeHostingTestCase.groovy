package example.test

import org.aost.test.StandaloneAostSeleneseTestCase
import example.google.GoogleCodeHosting
import org.aost.object.UrlLink

/**
 *  Sample test cases for Google Code host page at:
 *
 *       http://code.google.com/hosting/
 *   to demostrate nested Tables
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class GoogleCodeHostingTestCase extends StandaloneAostSeleneseTestCase {

    protected static GoogleCodeHosting gch

    public void initUi() {
        gch = new GoogleCodeHosting()
        gch.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testCodeLabelTable(){
        connectUrl("http://code.google.com/hosting/")
        String label = gch.getModuleLabel()
        assertEquals("Example project labels:", label)
        
        int nrow = gch.getLabelTableRowNum()
        assertEquals(3, nrow)

        int ncolumn = gch.getLabelTableColumnNum()
        assertEquals(6, ncolumn)
        
        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
               def obj = gch.getTableElement(i, j)
               assertTrue(obj instanceof UrlLink)
            }
        }
        Map map = gch.getAllLabels()
        assertEquals(18, map.size())

        int[] index = map.get("Java")
        assertNotNull(index)
        assertEquals(2, index.length)

        //find the url link
        String url = gch.getUrlLink(index[0], index[1])
        assertEquals("search?q=label%3aJava", url)

        //click on "Java" link
        gch.clickOnLable(index[0], index[1])
    }

}