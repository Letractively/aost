package org.telluriumsource.object

import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.event.Event

import org.json.simple.JSONObject
import org.json.simple.JSONArray

/**
 *  Basic UI object
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
abstract class UiObject implements Cloneable{

  /*
  Let UI Object be a pure data structure and only include placeholders for
  methods wich should be responsed to.

  decoupling them and let them be handled by DSL ddc framework


  TODO: some ui object must be reactive to certain Events such as kekUP,
   We must add that feature in so that the UI object will automatically add
   the event during the simulation.

  Another good idea is to let the ui object to be specific in the last element
  in the xpath, the element before it should be just a reference xpath.
  For example, an input box should be //input[@attribute] for the last part of
  the xpath. A url link usually should be //a[@attribute]
  In that way, we can have (reference xpath + inherent xpath) to be the actual
  xpath.
  For example,
    An input box has the xpath
       //div[contains(@uid,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@uid,'jupiter_widget_SearchTextbox')]/descendant::input[@type='button' and @dojoattachpoint='dap_findBtn']
    Here
       //div[contains(@uid,'showmanager_modlet_ShowManTaskCoordinator')]/descendant::div[contains(@uid,'jupiter_widget_SearchTextbox')]

    is the reference xpath and
       input[@type='button' and @dojoattachpoint='dap_findBtn'] is the inherent xpath, i.e., it is specific for that ui object

   The same idea can be applied to a group of composited ui objects.

   The composited ui objects always come with a reference xpath and each ui object inside the composited ui object
   has the xpath in the format:
        group reference xpath + reference xpath (related the the group reference xpath) + inherent xpath

  */
    public static final String KEY = "key"

    public static final String OBJECT = "obj"

    public static final String UI_TYPE = "uiType"

    //Template ID
    public static final String TID = "tid"
    String tid

    public static final String UID = "uid"
    String uid

    public static final String NAMESPACE = "namespace"
    String namespace = null

    public static final String LAZY = "lazy"
    //UI object is cacheable by default
    boolean cacheable = true

    public static final String LOCATOR = "locator"
    def locator

    //reference back to its parent
    def Container parent

    //respond to JavaScript events
    public static final String EVENTS = "events"
    String[] respondToEvents

    abstract JSONObject toJSON()

    protected JSONObject buildJSON(Closure c){
      JSONObject jso = new JSONObject()
      jso.put(UID, uid)
      if(!cacheable)
        jso.put(LAZY, this.cacheable)
      if(locator != null){
        jso.put(LOCATOR, locator.toJSON())
      }else{
        jso.put(LOCATOR, null)
      }
      if(namespace != null && namespace.trim().length() > 0)
        jso.put(NAMESPACE, namespace)
      if(respondToEvents != null && respondToEvents.length > 0){
        JSONArray jae = new JSONArray();
        for(String event: respondToEvents){
          jae.add(event);
        }
//        jso.put(EVENTS, respondToEvents)
        jso.put(EVENTS, jae);
      }
      
      if(c != null)
        c(jso)

      return jso
    }

    def mouseOver(Closure c){
        c(locator)
    }

    def mouseOut(Closure c){
        c(locator)
    }

    def dragAndDrop(String movementsString, Closure c){
        c(locator)
    }

    boolean isElementPresent(Closure c){
        return c(locator)
    }

    boolean isVisible(Closure c){
        return c(locator)
    }

    boolean isDisabled(Closure c){
        return c(locator)
    }

    boolean waitForElementPresent(int timeout, Closure c){
        return c(locator)
    }

    boolean waitForElementPresent(int timeout, int step, Closure c){
        return c(locator)
    }

    String getText(Closure c){
       return c(locator)
    }

    def getAttribute(String attribute, Closure c){
//        return c(locator, "@${attribute}")
//         return c(locator, "/self::node()@${attribute}")
      return c(locator, attribute)
    }

    def hasCssClass(Closure c){
//      return c(locator, "/self::node()@class")
      return c(locator, "class")
    }

    def toggle(Closure c){
      return c(locator)  
    }

    def methodMissing(String name, args) {
         //check if the click action is used and if the object can respond to the "Click" event
         //if it is, then call the "click" method, i.e., the innerClick method here
         if("click".equals(name) && isEventIncluded(Event.CLICK)){
             return this.invokeMethod("innerCall", args)
         }
         if("doubleClick".equals(name) && isEventIncluded(Event.DOUBLECLICK)){
             return this.invokeMethod("innerCall", args)
         }

        throw new MissingMethodException(name, UiObject.class, args)
    }

    boolean isEventIncluded(Event event){

        if (respondToEvents != null && event != null){
            for(String resp : respondToEvents){
                if(event.toString().equalsIgnoreCase(resp))
                    return true
            }
        }

        return false
    }

    protected innerCall(Closure c){
        c(locator)
    }

    public customMethod(Closure c){
       c(locator)
    }

    public String getXPath(Closure c){

      return c(locator)
    }

    public String getSelector(Closure c){
      return c(locator)
    }

    public String[] getCSS(String cssName, Closure c){
      return c(locator)
    }

    public String waitForText(int timeout, Closure c){
      return c(locator, timeout)
    }

    def diagnose(Closure c){
      c(locator)
    }

    String fullUid() {
        if (this.parent != null) {
            return this.parent.fullUid() + "." + this.tid;
        }

        return this.uid;
    }

    public boolean amICacheable(){
      //check its parent and do not cache if its parent is not cacheable
      //If an object is cacheable, the path from the root to itself should
      //be all cacheable
      if(parent != null)
        return this.cacheable && parent.amICacheable() && (!parent.noCacheForChildren)

      return this.cacheable
    }

    public String getIndent(){
      if(parent != null){
          return parent.getIndent() + "    ";
      }else{
        return "";
      }

    }

    public String generateHtml(){
      if(this.locator != null){
        return getIndent() + this.locator.generateHtml(true) + "\n";
      }

      return "\n";
    }

    public void traverse(WorkflowContext context){
       context.appendToUidList(context.getUid())
       context.popUid()
    }

    protected void jsonifyObject(WorkflowContext context) {
      JSONObject jso = new JSONObject();
      jso.put(KEY, this.fullUid());
      def juio = this.toJSON();
      jso.put(OBJECT, juio)
      JSONArray arr = context.getJSONArray();
      arr.add(jso)
      context.setJSONArray(arr);
    }

    public void treeWalk(WorkflowContext context){
//       context.appendToUidList(context.getUid())
       this.jsonifyObject(context)
//       context.popUid()
    }

    //walkTo through the object tree to until the Ui Object is found by the UID
    public UiObject walkTo(WorkflowContext context, UiID uiid){
        //if not child listed, return itself
        if(uiid.size() < 1)
            return this

        //otherwise,
        //by default, regular ui objects cannot walkTo forward to the child
        // since only Container can have children, override this method
        // for container or its subclasses

        //cannot find child
        String child = uiid.pop()

        println(i18nBundle.getMessage("Container.CannotFindUIObject" , {[child , this.uid]}))

        return null
    }
}