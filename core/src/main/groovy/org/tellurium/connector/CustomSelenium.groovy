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
    protected String userExtension = null

    CustomSelenium(CommandProcessor commandProcessor) {
      super (commandProcessor)
    }

    public void setUserExt(String userExt){
      this.userExtension = userExt
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
      CommandProcessor processor = sel.commandProcessor
      CustomSelenium csel = new CustomSelenium(processor)
/*
      if(this.userExtension != null && this.userExtension.trim().length() > 0){
        File userExt = new File(this.userExtension);
//        processor.setExtensionJs(userExt.getAbsolutePath())
        processor.setExtensionJs(this.userExtension)
        println "Add user-extensions.js found at given path: " + userExt.getAbsolutePath() + " to Command Processor";
      }
 */
      csel.customClass = this.customClass
      csel.passCommandProcessor(processor)

      return csel
    }

    /*Please add custom methods here for Selenium RC after you add user extension to Selenium Core

   For instance,

       public void typeRepeated(String locator, String text) {

         commandProcessor.doCommand("typeRepeated", new String[]{locator, text});

       }
    */

    def String getAllText(String locator){
		String[] arr = [locator];
      
		return commandProcessor.doCommand("getAllText", arr);
	}

    def String getCSS(String locator, String cssName){
		String[] arr = [locator, cssName];

		return commandProcessor.doCommand("getCSS", arr);
	}

    def Number getJQuerySelectorCount(String locator){
		String[] arr = [locator];

		return commandProcessor.getNumber("getJQuerySelectorCount", arr);
	}

    def Number getListSize(String locator, String separators){
      String[] arr = [locator, separators];

      return commandProcessor.getNumber("getListSize", arr);
    }

    def boolean isDisabled(String locator){
      String[] arr = [locator];

      return commandProcessor.getBoolean("isDisabled", arr);
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

      return commandProcessor.getBoolean("getCacheState", arr);
    }

    public void setCacheMaxSize(int size){
      String[] arr = [size];
      commandProcessor.doCommand("setCacheMaxSize",  arr);
    }

    public Number getCacheSize(){
    	String[] arr = [];

        return commandProcessor.getNumber("getCacheSize", arr);
    }

    public Number getCacheMaxSize(){
    	String[] arr = [];

        return commandProcessor.getNumber("getCacheMaxSize", arr);
    }

    public String getCacheUsage(){
       	String[] arr = [];

		return commandProcessor.doCommand("getCacheUsage", arr);
    }

    public void addNamespace(String prefix, String namespace){
       String[] arr = [prefix, namespace];
       commandProcessor.doCommand("addNamespace", arr);
    }

    public String getNamespace(String prefix){
       String[] arr = [prefix];

       return commandProcessor.getString("getNamespace", arr);
    }

    public void useDiscardNewCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardNewPolicy", arr);
    }

    public void useDiscardOldCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardOldPolicy", arr);
    }

    public void useDiscardLeastUsedCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardLeastUsedPolicy", arr);
    }
  
    public void useDiscardInvalidCachePolicy(){
        String[] arr = [];
        commandProcessor.doCommand("useDiscardInvalidPolicy", arr);
    }

    public String getCurrentCachePolicy(){
        String[] arr = [];

        return commandProcessor.getString("getCachePolicyName", arr);
    }

    public void typeKey(String locator, String key){
        String[] arr = [locator, key];
        commandProcessor.doCommand("typeKey", arr);
    }

    public String issueBundle(String json){
        String[] arr = [json];

        return commandProcessor.doCommand("getBundleResponse", arr);
    }

    public void useUiModule(String json){
        String[] arr = [json];
        commandProcessor.doCommand("useUiModule", arr);
    }

    public boolean isUiModuleCached(String id){
       String[] arr = [id];

       return commandProcessor.getBoolean("isUiModuleCached", arr);     
    }
}