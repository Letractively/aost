package example.test.java;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.tellurium.test.java.TelluriumJavaTestCase;
import example.other.TelluriumIssueModule;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModule tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssueModule();
        tisp.defineUi();
    }

    @Before
    public void setUp(){
        connectUrl("http://code.google.com/p/aost/issues/list");
    }

    @Test
    public void testAllIssues(){
        String[] details = tisp.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }

    @Test
    public void testGetCellCount(){
        int count = tisp.getTableCellCount();
        assertTrue(count > 0);
        System.out.println("Cell size: " + count);
    }
    
}
