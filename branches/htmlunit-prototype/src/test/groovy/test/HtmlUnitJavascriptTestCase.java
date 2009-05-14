package test;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * 
 *        
 *         Date: May 14, 2009
 */
public class HtmlUnitJavascriptTestCase {

    @Test
    public void documentWrite() throws Exception {
        final WebClient webClient = new WebClient();

        final HtmlPage page = webClient.getPage("http://myserver/test.html");
        final HtmlForm form = page.getFormByName("form1");
        for (int i = 1; i <= 5; i++) {
            final String expectedName = "textfield" + i;
            assertEquals(
                    "text",
                    form.<HtmlInput>getInputByName(expectedName).getTypeAttribute());
        }
    }
}
