package example.test.java;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.tellurium.test.java.TelluriumJavaTestCase;
import example.other.TelluriumIssueModule;

public class TelluriumIssueTestCase extends TelluriumJavaTestCase {
    private static TelluriumIssueModule tisp;

    @BeforeClass
    public static void initUi() {
        tisp = new TelluriumIssueModule();
        tisp.defineUi();
    }

    @Test
    public void testAllIssues(){
        connectUrl("http://code.google.com/p/aost/issues/list");
        String[] details = tisp.getAllText();
        assertNotNull(details);
        for(String content: details){
            System.out.println(content);
        }
    }
    
}
