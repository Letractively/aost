package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: May 14, 2009
 */
public class HtmlUnitGetStartTestCase {
    
    @Test
    public void homePage() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
        assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
    }

    @Test
    public void homePage_Firefox() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_2);
        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
        assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
    }

    @Test
    public void getElements() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
        final HtmlDivision div = page.getHtmlElementById("some_div_id");
        final HtmlAnchor anchor = page.getAnchorByName("anchor_name");
    }

    @Test
    public void xpath() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");

        //get list of all divs
        final List<?> divs = page.getByXPath("//div");

        //get div which has a 'name' attribute of 'John'
        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@name='John']").get(0);
    }

    @Test
    public void homePage_proxy() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_2, "http://myproxyserver", 8080);

        //set proxy username and password
        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
        credentialsProvider.addProxyCredentials("username", "password");

        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
        assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
    }

    @Test
    public void submittingForm() throws Exception {
        final WebClient webClient = new WebClient();

        // Get the first page
        final HtmlPage page1 = webClient.getPage("http://some_url");

        // Get the form that we are dealing with and within that form,
        // find the submit button and the field that we want to change.
        final HtmlForm form = page1.getFormByName("myform");

        final HtmlSubmitInput button = form.getInputByName("submitbutton");
        final HtmlTextInput textField = form.getInputByName("userid");

        // Change the value of the text field
        textField.setValueAttribute("root");

        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();
    }
}
