package org.tellurium.test

import org.tellurium.bundle.SelenCmd
import org.tellurium.bundle.CommandBundle

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 22, 2009
 * 
 */

public class CommandBundle_UT extends GroovyTestCase {

//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]"],"name":"mouseOver","sequ":1},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","focus"],"name":"fireEvent","sequ":2},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyDown","sequ":3},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyPress","sequ":4},
//  {"uid":"Google.Input","args":["jquery=table input[title=Google Search][name=q]","t"],"name":"keyUp","sequ":5}

  public void testExtractAllAndConvertToJson(){
    SelenCmd cmd1 = new SelenCmd(1, "Google.Input", "mouseOver", ["jquery=table input[title=Google Search][name=q"]);
    SelenCmd cmd2 = new SelenCmd(2, "Google.Input", "fireEvent", ["jquery=table input[title=Google Search][name=q", "focus"]);
    SelenCmd cmd3 = new SelenCmd(3, "Google.Input", "keyDown", ["jquery=table input[title=Google Search][name=q", "t"]);
    SelenCmd cmd4 = new SelenCmd(4, "Google.Input", "keyPress", ["jquery=table input[title=Google Search][name=q", "t"]);
    SelenCmd cmd5 = new SelenCmd(5, "Google.Input", "keyUp", ["jquery=table input[title=Google Search][name=q", "t"]);

    CommandBundle bundle = new CommandBundle();
    bundle.addToBundle(cmd1);
    bundle.addToBundle(cmd2);
    bundle.addToBundle(cmd3);
    bundle.addToBundle(cmd4);
    bundle.addToBundle(cmd5);
    assertEquals(5, bundle.size());
    String json = bundle.extractAllAndConvertToJson();
    assertEquals(0, bundle.size());
    println(json);
  }


}