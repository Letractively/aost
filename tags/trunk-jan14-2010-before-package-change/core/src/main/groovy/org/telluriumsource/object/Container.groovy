package org.telluriumsource.object

import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.dsl.UiID
import org.telluriumsource.locator.LocatorProcessor
import org.telluriumsource.locator.GroupLocateStrategy
import org.json.simple.JSONObject
import org.telluriumsource.framework.Environment;
import org.telluriumsource.i18n.IResourceBundle;

/**
 *  container
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Container extends UiObject {

    public static final String GROUP = "group"
    protected IResourceBundle i18nBundle

    //if it uses group informtion to infer its locator
    protected boolean useGroupInfo = false

    //If you have Ajax application and the container's children keep changing
    //It is wise to force the children not to use cache

    public static final String NO_CACHE_FOR_CHILDREN = "noCacheForChildren"
    protected boolean noCacheForChildren = false

    //since we use map, the component name must be unique
    def components = [:]

    public Container(){
    	  i18nBundle = Environment.instance.myResourceBundle()
    }
    def add(UiObject component){
        components.put(component.uid, component)
    }

    def getComponent(String id){
        components.get(id)
    }

    public boolean useGroup(){
      return this.useGroupInfo
    }

    @Override
    protected JSONObject buildJSON(Closure c){
      JSONObject jso = new JSONObject()
      jso.put(UID, uid)
      if (!cacheable)
        jso.put(LAZY, this.cacheable)
      jso.put(LOCATOR, locator.toJSON())
      if (namespace != null && namespace.trim().length() > 0)
        jso.put(NAMESPACE, namespace)
      if (respondToEvents != null && respondToEvents.length > 0)
        jso.put(EVENTS, respondToEvents)
//      if (this.useGroupInfo)
//        jso.put(GROUP, this.useGroupInfo)
      if (this.noCacheForChildren)
        jso.put(NO_CACHE_FOR_CHILDREN, this.noCacheForChildren)

      if(c != null)
          c(jso)

      return jso
    }

    public JSONObject toJSON() {

      return this.buildJSON(){jso ->
        jso.put(UI_TYPE, "Container")
      }
    }

    protected void groupLocating(WorkflowContext context){
      if (this.useGroupInfo) {
        if(context.isUseCssSelector()){
          context.appendReferenceLocator(GroupLocateStrategy.select(this))
        }else{
          //need to use group information to help us locate the container xpath
          context.appendReferenceLocator(GroupLocateStrategy.locate(this))
        }
      } else {
        //do not use the group information, process as regular
        def lp = new LocatorProcessor()
        context.appendReferenceLocator(lp.locate(context, this.locator))
      }
    }

    @Override
    public void traverse(WorkflowContext context){
      context.appendToUidList(context.getUid())
      components.each {key, component->
        context.pushUid(key)
        component.traverse(context)
      }
      context.popUid()
    }

    @Override
    public void treeWalk(WorkflowContext context){
      this.jsonifyObject(context)
      components.each {key, component->
        if(component.cacheable){
          component.treeWalk(context)
        }
      }
    }

    @Override
    public String generateHtml(){
      StringBuffer sb = new StringBuffer(64);
      String indent = getIndent();

      if(this.components.size() > 0){
        if(this.locator != null)
          sb.append(indent + this.locator.generateHtml(false)).append("\n");
        this.components.each {String uid, UiObject obj ->
          sb.append(obj.generateHtml());
        }
        if(this.locator != null)
          sb.append(indent + this.locator.generateCloseTag()).append("\n");
      }else{
        if(this.locator != null){
          sb.append(this.locator.generateHtml(true)).append("\n")
        }
      }

      return sb.toString();
    }

    //walkTo through the object tree to until the UI object is found by the UID from the stack
    @Override
    public UiObject walkTo(WorkflowContext context, UiID uiid){

        //if not child listed, return itself
        if(uiid.size() < 1){
            if(this.locator != null && this.useGroupInfo){
                groupLocating(context)
            }

            return this
        }

        String child = uiid.pop()

        //otherwise, try to find its child
        UiObject cobj = components.get(child)

        //if found the object
        if(cobj != null){
            //update reference locator by append the relative locator for this container
            if(this.locator != null){
//              context.appendReferenceLocatorForUiObject(this)
                groupLocating(context)

/*                if(this.useGroupInfo){
                    //need to use group information to help us locate the container xpath
                    context.appendReferenceLocator(GroupLocateStrategy.locate(this))
                }else{
                    //do not use the group information, process as regular
                    def lp = new LocatorProcessor()
                    context.appendReferenceLocator(lp.locate(this.locator))
                }*/

            }
            if(uiid.size() < 1){
                //not more child needs to be found
                return cobj
            }else{
                //recursively call walkTo until the object is found
                return cobj.walkTo(context, uiid)
            }
        }else{

            //cannot find the object

            println i18nBundle.getMessage("Container.CannotFindUIObject" , child , this.uid)

            return null
        }
    }
}