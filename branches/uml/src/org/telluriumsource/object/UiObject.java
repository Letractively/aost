package org.telluriumsource.object;

import org.telluriumsource.dsl.UiID;
import org.telluriumsource.dsl.WorkflowContext;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Oct 1, 2009
 */
public abstract class UiObject {
    String uid;

    String namespace = null;

    Locator locator;

    String[] respondToEvents;

    public void mouseOver(){

    }

    public void mouseOut(){

    }

    public void dragAndDrop(String movements){

    }

    boolean isElementPresent(){
        return false;
    }

    boolean isVisible(){
        return false;
    }

    boolean isDisabled(){
        return false;
    }

    boolean waitForElementPresent(int timeout){
        return false;
    }

    boolean waitForElementPresent(int timeout, int step){
        return false;
    }

    String getText(){
       return null;
    }

    String getAttribute(String attribute){

      return null;
    }

    boolean hasCssClass(){

      return false;
    }

    public Object customMethod(){
        return null;
    }

    public String getXPath(){

      return null;
    }

    public String getSelector(){
      return null;
    }

    public String[] getCSS(String cssName){
      return null;
    }

    public String waitForText(int timeout){
      return null;
    }

    public void diagnose(){
    }

    public String generateHtml(){
        return null;
    }

    protected UiObject walkTo(WorkflowContext context, UiID uiid){
        return null;
    }
}
