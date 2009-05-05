package example.test.groovy

import example.other.TelluriumIssueModule
import org.tellurium.test.groovy.TelluriumGroovyTestCase

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 4, 2009
 * 
 */

public class TelluriumIssueGroovyTestCase extends TelluriumGroovyTestCase{

  private TelluriumIssueModule tisp;

  public void setUp() {
    setUpForClass()
    tisp = new TelluriumIssueModule();
    tisp.defineUi();
  }

  public void tearDown() {
    tearDownForClass()
  }

  public void testDumpWithXPath() {
    tisp.disableJQuerySelector();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelector() {
    tisp.useJQuerySelector();
    tisp.disableSelectorCache();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelectorCacheEnabled() {
    tisp.useJQuerySelector();
    tisp.enableSelectorCache();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }
}