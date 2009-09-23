package org.tellurium.example.test.groovy

import org.tellurium.example.other.TelluriumIssueModule
import org.tellurium.test.groovy.TelluriumGroovyTestCase

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 4, 2009
 * 
 */

public class TelluriumIssueGroovyTestCase extends GroovyTestCase{

  private org.tellurium.example.other.TelluriumIssueModule tisp;

  public void setUp() {
    tisp = new org.tellurium.example.other.TelluriumIssueModule();
    tisp.defineUi();
  }

  public void tearDown() {
  }

  public void testDumpWithXPath() {
    tisp.disableJQuerySelector();
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelector() {
    tisp.useJQuerySelector();
//    tisp.disableSelectorCache();
    tisp.exploreSelectorCache = false;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }

  public void testDumpWithJQuerySelectorCacheEnabled() {
    tisp.useJQuerySelector();
//    tisp.enableSelectorCache();
    tisp.exploreSelectorCache = true;
    tisp.dump("issueSearch");
    tisp.dump("issueSearch.searchButton");
    tisp.dump("issueResult");
  }
}