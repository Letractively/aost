package org.tellurium.dsl

import org.tellurium.locator.XPathProcessor
import org.tellurium.object.UiObject
import org.tellurium.object.Container
import org.tellurium.locator.LocatorProcessor
import org.tellurium.locator.GroupLocateStrategy

/**
 * Hold metadata for execution workflow
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 2, 2008
 *
 */
class WorkflowContext {

  public static final String REFERENCE_LOCATOR = "Reference_Locator"
  public static final String OPTION_LOCATOR = "Option_Locator"
  public static final String MATCH_ALL = "*"

  private boolean useOption = false
  //Table's child object's tag will be duplicated with the current relative xpath provided by xpath
  //For example, /table/tr/td + /td/input
  private boolean tableDuplicateTag = false

  private boolean exploreJQuerySelector = false

  def context = [:]

  public void setTableDuplicateTag() {
    this.tableDuplicateTag = true
  }

  public boolean isUseOption() {
    return useOption
  }

  public void setUseOption(boolean useOption) {
    this.useOption = useOption
  }

  public def getOptionLocator() {
    return context.get(OPTION_LOCATOR)
  }

  public Object getContext(String name) {

    return context.get(name)
  }

  public void putContext(String name, Object obj) {
    context.put(name, obj)
  }

  public static WorkflowContext getDefaultContext() {

    WorkflowContext context = new WorkflowContext()
    context.putContext(REFERENCE_LOCATOR, "")

    return context
  }

  public static WorkflowContext getContextByStrategy(boolean useJQuerySelector){
    WorkflowContext context = new WorkflowContext()

    context.setJQuerySelector(useJQuerySelector)

    context.putContext(REFERENCE_LOCATOR, "")

    return context
  }

  public boolean isUseJQuerySelector(){

    return this.exploreJQuerySelector
  }

  public void disableJQuerySelector(){
    this.exploreJQuerySelector = false
  }

  public void setJQuerySelector(boolean useJQuerySelector){
    this.exploreJQuerySelector = useJQuerySelector
  }

  public String getReferenceLocator() {

    return context.get(REFERENCE_LOCATOR)
  }

  public void setReferenceLocator(String loc) {

    context.put(REFERENCE_LOCATOR, loc)
  }

  //append the relative locator to the end of the reference locator
  public void appendReferenceLocator(String loc) {

    //matching all does not work for jQuery selector, skip it
    if(this.exploreJQuerySelector && MATCH_ALL.equals(loc.trim())){
      return
    }

    String rl = context.get(REFERENCE_LOCATOR)

    if (loc != null) {
      if (rl == null) {
        rl = loc
      } else {
        if (this.tableDuplicateTag && (!this.exploreJQuerySelector)) {
          this.tableDuplicateTag = false
          rl = this.checkTableDuplicateTag(rl, loc)
        } else {
          rl = rl + loc
        }
      }
    }
    
    context.put(REFERENCE_LOCATOR, rl)
  }

  protected String checkTableDuplicateTag(String rl, String loc) {

    String xp = XPathProcessor.lastXPath(rl)
    String tag = XPathProcessor.getTagFromXPath(xp)
    
    //assume loc is only the xpath for one element
    String ntag = XPathProcessor.getTagFromXPath(loc)
    if (tag.equals(ntag)) {
      int pos = XPathProcessor.checkPosition(xp)
      if (pos != -1) {
        String nloc = XPathProcessor.addPositionAttribute(loc, pos)
        rl = XPathProcessor.popXPath(rl) + nloc
      }else{
        rl = XPathProcessor.popXPath(rl) + loc
      }
    }else{
      rl = rl + loc
    }

    return rl
  }
}