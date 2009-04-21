package org.tellurium.connector

import com.thoughtworks.selenium.DefaultSelenium
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*
import com.thoughtworks.selenium.Selenium
import com.thoughtworks.selenium.CommandProcessor
import org.tellurium.exception.*

/**
 * Customize Selenium RC so that we can add custom methods to Selenium RC
 * Added Selenium Grid support.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 * @author Haroon Rasheed (haroonzone@gmail.com
 *
 * Date: Oct 21, 2008
 * 
 */
class CustomSelenium extends DefaultSelenium {
    
    CustomSelenium(CommandProcessor commandProcessor) {
      super (commandProcessor)     

    }

    // Added for the selenium grid support.
    // Start the selenium session specified by the arguments.
    // and register the selenium rc with Selenium HUB
    def void startSeleniumSession(String host, int port, String browser, String url) throws Exception{
      try{
        startSeleniumSession(host, port, browser, url)
      }catch (Exception e){
        throw new TelluriumException ("Cannot start selenium:"+e.getMessage())
      }


    }

    // Close the selenium session and unregister the Selenium RC
    // from Selenium Hub
    def void closeSeleniumSession() throws Exception{
      try{
        closeSeleniumSession()
      }catch (Exception e){
        throw new TelluriumException ("Cannot close selenium:"+e.getMessage())        
      }


    }

    // Get the active Selenium RC session
    def Selenium getActiveSeleniumSession(){
      return session()
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

    public void useSelectorCache(){
      String[] arr = [];
      commandProcessor.doCommand("doUseCache", arr);
    }

    public void disableSelectorCache(){
      String[] arr = [];
      commandProcessor.doCommand("doDisableCache", arr);
    }

    def boolean getCacheState(){
      String[] arr = [];
      boolean result = commandProcessor.getBoolean("getCacheState", arr);
      return result;
    }
}