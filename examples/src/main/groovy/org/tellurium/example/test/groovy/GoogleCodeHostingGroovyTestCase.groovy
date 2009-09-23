package org.tellurium.example.test.groovy

import org.tellurium.test.groovy.TelluriumGroovyTestCase
import org.tellurium.example.google.GoogleCodeHosting
import org.tellurium.object.UrlLink
import org.tellurium.example.google.NewGoogleCodeHosting

/**
 *  Sample test cases for Google Code host page at:
 *
 *       http://code.google.com/hosting/
 *   to demostrate nested Tables
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class GoogleCodeHostingGroovyTestCase extends TelluriumGroovyTestCase {

    public void initUi() {
    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testCodeLabelTable(){
        //test google code hosting using Selenium way, i.e., absolute xpath
        GoogleCodeHosting gch = new GoogleCodeHosting()
        gch.defineUi()
        codeLabelTableTest(gch)

        //test google code hosting using composite locators
        NewGoogleCodeHosting ngch =  new NewGoogleCodeHosting()
        ngch.defineUi()
        codeLabelTableTest(ngch)
    }

    void codeLabelTableTest(gch){
        connectUrl("http://code.google.com/hosting/")
        String label = gch.getModuleLabel()
        assertEquals("Explore hosted projects:", label)
        
        int nrow = gch.getLabelTableRowNum()
        assertEquals(4, nrow)

        int ncolumn = gch.getLabelTableColumnNum()
        assertEquals(6, ncolumn)
        
        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
               def obj = gch.getTableElement(i, j)
               assertTrue(obj instanceof UrlLink)
            }
        }
        Map map = gch.getAllLabels()
//        assertEquals(18, map.size())

        int[] index = map.get("Java")
        assertNotNull(index)
        assertEquals(2, index.length)

        //find the url link
        String url = gch.getUrlLink(index[0], index[1])
        assertEquals("http://code.google.com/hosting/search?q=label%3aJava", url)

        //click on "Java" link
        gch.clickOnLable(index[0], index[1])
    }

}