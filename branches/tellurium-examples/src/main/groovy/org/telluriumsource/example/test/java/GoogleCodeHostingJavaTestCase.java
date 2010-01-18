package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.ui.object.UrlLink;
import org.telluriumsource.example.google.NewGoogleCodeHosting;
import static org.junit.Assert.*;
import org.junit.*;

import java.util.Map;
import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Aug 2, 2008
 */
public class GoogleCodeHostingJavaTestCase extends TelluriumJavaTestCase {
    private static NewGoogleCodeHosting ngch;


    @BeforeClass
    public static void initUi() {
        ngch = new NewGoogleCodeHosting();
        ngch.defineUi();
        connectSeleniumServer();
        useTrace(true);
        useCache(true);
    }

    @AfterClass
    public static void setUpAfterClass(){

    }
    
    @Before
    public void setUpBeforeTest(){
        connectUrl("http://code.google.com/hosting/");
    }

    @After
    public void tearDownAfterTest(){

    }

    @Test
    public void dumpUiModule(){
        String uim = ngch.jsonify("labels_table");
//  [{"obj":{"uid":"labels_table","locator":{"tag":"table"},"uiType":"Table"},"key":"labels_table"},{"obj":{"uid":"row: 1, column: 1","locator":{"text":"Explore hosted projects:","tag":"div"},"uiType":"TextBox"},"key":"labels_table[1][1]"},{"obj":{"uid":"row: 2, column: 1","locator":{"tag":"table","header":"\/div[@id=\"popular\"]"},"uiType":"Table"},"key":"labels_table[2][1]"},{"obj":{"uid":"row: 2, column: 1","locator":{"tag":"table","header":"\/div[@id=\"popular\"]"},"uiType":"Table"},"key":"labels_table[2][1][1][1]"},{"obj":{"uid":null,"locator":null,"uiType":"TextBox"},"key":"labels_table[3][2]"}]
      
        System.out.println(uim);
    }

    @Test
    public void testCodeLabelTable(){
        String label = ngch.getModuleLabel();
        assertEquals("Explore hosted projects:", label);

        int nrow = ngch.getLabelTableRowNum();
        assertEquals(4, nrow);

        int ncolumn = ngch.getLabelTableColumnNum();
        assertEquals(6, ncolumn);

        for(int i=1; i<=nrow; i++){
            for(int j=1; j<=ncolumn; j++){
               Object obj = ngch.getTableElement(i, j);
               assertTrue(obj instanceof UrlLink);
            }
        }
    }

    @Test
    public void testClickOnLabel(){
        Map map = ngch.getAllLabels();
//        assertEquals(18, map.size());

        List index = (List) map.get("Java");
        assertNotNull(index);
        assertEquals(2, index.size());

        //find the url link
        int first = (Integer)index.get(0);
        int second = (Integer)index.get(1);
        String url = ngch.getUrlLink(first, second);
        assertEquals("http://code.google.com/hosting/search?q=label%3aJava", url);

        //click on "Java" link
        ngch.clickOnLable(first, second);

    }

}
