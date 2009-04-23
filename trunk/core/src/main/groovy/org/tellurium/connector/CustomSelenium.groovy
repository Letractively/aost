package org.tellurium.connector

import com.thoughtworks.selenium.DefaultSelenium
//import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*
import com.thoughtworks.selenium.Selenium
import com.thoughtworks.selenium.CommandProcessor
import org.tellurium.exception.*
import org.tellurium.config.Configurable

/**
 * Customize Selenium RC so that we can add custom methods to Selenium RC
 * Added Selenium Grid support.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com)
 *
 * Date: Oct 21, 2008
 * 
 */
class CustomSelenium extends DefaultSelenium {

    protected CustomCommand customClass = null

    CustomSelenium(CommandProcessor commandProcessor) {
      super (commandProcessor)
    }

    protected void passCommandProcessor(CommandProcessor commandProcessor){
      if(customClass != null){
        customClass.setProcessor(this.commandProcessor) 
      }
    }

    protected def methodMissing(String name, args) {

         if(customClass != null && customClass.metaClass.respondsTo(customClass, name, args)){
              return customClass.invokeMethod(name, args)
         }

        throw new MissingMethodException(name, CustomSelenium.class, args)
    }
  
    // Added for the selenium grid support.
    // Start the selenium session specified by the arguments.
    // and register the selenium rc with Selenium HUB
    def void startSeleniumSession(String host, int port, String browser, String url) throws Exception{
      try{
        com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession(host, port, browser, url)
      }catch (Exception e){
        throw new TelluriumException ("Cannot start selenium:"+e.getMessage())
      }


    }

    // Close the selenium session and unregister the Selenium RC
    // from Selenium Hub
    def void closeSeleniumSession() throws Exception{
      try{
        com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession()
      }catch (Exception e){
        throw new TelluriumException ("Cannot close selenium:"+e.getMessage())        
      }


    }

    // Get the active Selenium RC session
    def CustomSelenium getActiveSeleniumSession(){
      DefaultSelenium sel =  com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session()
      CustomSelenium csel = new CustomSelenium(sel.commandProcessor)
      csel.customClass = this.customClass
      csel.passCommandProcessor(sel.commandProcessor)

      return csel
    }

    /*Please add custom methods here for Selenium RC after you add user extension to Selenium Core

   For instance,

       public void typeRepeated(String locator, String text) {

         commandProcessor.doCommand("typeRepeated", new String[]{locator, text});

       }
    */
  
	def String getSelectorProperties(String jqSelector, String props){
		String[] arr = [jqSelector, props];
		String st = commandProcessor.doCommand("getSelectorProperties", arr);
		return st;
	}

	def String getSelectorText(String jqSelector){
		String[] arr = [jqSelector];
		String st = commandProcessor.doCommand("getSelectorText", arr);
		return st;
	}

	def String getSelectorFunctionCall(String jqSelector, String args){
		String[] arr = [jqSelector, args];
		String st = commandProcessor.doCommand("getSelectorFunctionCall", arr);
		return st;
	}

    def String getAllText(String locator){
		String[] arr = [locator];
		String st = commandProcessor.doCommand("getAllText", arr);
		return st;
	}

    def String getCSS(String locator, String cssName){
		String[] arr = [locator, cssName];
		String st = commandProcessor.doCommand("getCSS", arr);
		return st;
	}

    def Number getJQuerySelectorCount(String locator){
		String[] arr = [locator];
		Number num = commandProcessor.getNumber("getJQuerySelectorCount", arr);
		return num;
	}

    def boolean isDisabled(String locator){
      String[] arr = [locator];
      boolean result = commandProcessor.getBoolean("isDisabled", arr);
      return result;
    }

    public void enableSelectorCache(){
      String[] arr = [];
      commandProcessor.doCommand("enableCache", arr);
    }

    public void disableSelectorCache(){
      String[] arr = [];
      commandProcessor.doCommand("disableCache",  arr);
    }

    public void cleanSelectorCache(){
      String[] arr = [];
      commandProcessor.doCommand("cleanCache", arr);
    }
  
    def boolean getCacheState(){
      String[] arr = [];
      boolean result = commandProcessor.getBoolean("getCacheState", arr);
      return result;
    }
}