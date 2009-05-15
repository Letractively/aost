package test;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.html.*;

import java.util.List;

/**
 * 
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
        final HtmlDivision div = page.getHtmlElementById("leftColumn");
        final HtmlAnchor anchor = page.getAnchorByName("HtmlUnit");
    }

    @Test
    public void xpath() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");

        //get list of all divs
        final List<?> divs = page.getByXPath("//div");

        //get div which has a 'name' attribute of 'John'
        final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@class='xright']").get(0);
    }

    @Ignore
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
        final HtmlPage page1 = webClient.getPage("http://code.google.com/p/aost/");

        // Get the form that we are dealing with and within that form,
        // find the submit button and the field that we want to change.
        final HtmlForm form = page1.getFirstByXPath("//form[@action='/hosting/search']"); //.getFormByName("myform");

        final HtmlSubmitInput button = form.getInputByName("projectsearch");
        final HtmlTextInput textField = form.getInputByName("q");

        // Change the value of the text field
        textField.setValueAttribute("Tellurium DSL");

        // Now submit the form by clicking the button and get back the second page.
        final HtmlPage page2 = button.click();
    }
}
