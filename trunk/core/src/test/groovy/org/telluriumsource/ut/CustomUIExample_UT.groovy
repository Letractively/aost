package org.telluriumsource.ut

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 27, 2009
 * 
 */

public class CustomUIExample_UT extends GroovyTestCase {
  
  public void testDump(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    cue.dump("switch");
  }

  public void testCustomUICall(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    int tabnum = 3;
//    String status = cue.getXCaseStatus(tabnum); 
  }

  public void testGroup(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    cue.dump("caseRecordPopUp");    
  }

  public void testSubNav(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    cue.dump("subnav");        
  }

  public void testAccountEdit(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    cue.dump("accountEdit");
    cue.enableCssSelector();
    cue.dump("accountEdit");
    println cue.generateHtml("accountEdit");
  }

  public void testPartialMatching(){
    CustomUIExample cue = new CustomUIExample();
    cue.defineUi();
    cue.dump("confirmation");
  }
}