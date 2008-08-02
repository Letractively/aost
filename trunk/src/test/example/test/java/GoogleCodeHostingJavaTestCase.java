package example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.object.UrlLink;
import org.junit.*;
import example.google.NewGoogleCodeHosting;
import static org.junit.Assert.*;

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
    public void testCodeLabelTable(){
        String label = ngch.getModuleLabel();
        assertEquals("Example project labels:", label);

        int nrow = ngch.getLabelTableRowNum();
        assertEquals(3, nrow);

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
        assertEquals(18, map.size());

        List<Integer> index = (List) map.get("Java");
        assertNotNull(index);
        assertEquals(2, index.size());

        //find the url link
        String url = ngch.getUrlLink(index.get(0), index.get(1));
        assertEquals("search?q=label%3aJava", url);

        //click on "Java" link
        ngch.clickOnLable(index.get(0), index.get(1));

    }

}
