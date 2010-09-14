package org.telluriumsource.component.dispatch

import org.telluriumsource.component.client.SeleniumClient
import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.crosscut.trace.DefaultExecutionTracer
import org.telluriumsource.crosscut.trace.ExecutionTracer
import org.telluriumsource.framework.Environment
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.util.Helper
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.component.connector.CustomSelenium

class Dispatcher implements Configurable {
    public static final String PLACE_HOLDER = "\\?"
    protected IResourceBundle i18nBundle ;

    private String filenamePattern = "Screenshot?.png";

//    private SeleniumClient sc = new SeleniumClient();
    private RuntimeEnvironment env;

    private CustomSelenium sel;

    private ExecutionTracer tracer = new DefaultExecutionTracer();

/*    public Dispatcher(){
    	i18nBundle = Environment.instance.myResourceBundle()
    }*/
  
    public boolean isConnected(){
      //TODO: sometimes, the selenium client is not singleton ??  Fix it
/*      if(sc.client == null)
        sc = new SeleniumClient()
      
      if(sc.client == null || sc.client.getActiveSeleniumSession() == null)
        return false;*/
      if(sel == null || (!sel.isConnected()))
        return false;

      return true;
    }

    private boolean isUseScreenshot(){
      return env.isUseScreenshot();
    }

    private boolean isUseTrace(){
      return env.isUseTrace();
    }

    private boolean isGenerateBugReport(){
      return env.isUseBugReport();
    }

    def methodMissing(String name, args) {
      WorkflowContext context = args[0]
      String apiname = context.getApiName();
      Object[] params = Helper.removeFirst(args);

      //TODO: sometimes, the selenium client is not singleton ??  Fix it
      //here reset selenium client to use the new singleton instance which has the client set
//      if (sc.client == null || sc.client.getActiveSeleniumSession() == null)
//        sc = new SeleniumClient()

      try {
        long beforeTime = System.currentTimeMillis()
        def result = sel.metaClass.invokeMethod(sel, name, params)
        long duration = System.currentTimeMillis() - beforeTime
        if (isUseTrace())
          tracer.publish(apiname, beforeTime, duration)

        return result
      } catch (Exception e) {
        Environment.instance.setLastError(e);

        if (isUseScreenshot()) {
          long timestamp = System.currentTimeMillis()
          String filename = filenamePattern.replaceFirst(PLACE_HOLDER, "${timestamp}")
          sel.captureScreenshot(filename)
          println i18nBundle.getMessage("Dispatcher.ExceptionMessage", e.getMessage(), filename)
        }

        if(isGenerateBugReport()){
          bugReport();
        }else{
          //dump Environment variables
          println env.toString();
        }

        throw e
      }
  }

 /*
 protected def methodMissing(String name, args) {
    return invokeMethod(name, args)
  }
*/

  public void bugReport() {
/*    Environment env = Environment.instance;
    if (env.lastDslContext != null) {
      env.lastDslContext.bugReport();
    }*/

  }

/*  public void bugReport() {
    println "Please cut and paste the following bug report to Tellurium user group http://groups.google.com/group/tellurium-users"
    println "---------------------------- Bug Report --------------------------------"

    Environment env = Environment.instance;
    if (env.lastUiModule != null && env.lastDslContext != null) {
      println "UI Module " + env.lastUiModule + ": ";
      println env.lastUiModule.toString(env.lastUiModule);

    }

    if (env.lastDslContext != null) {
      println "HTML Source: ";

      println env.lastDslContext.getHtmlSource();
    }

    if (env.lastUiModule != null && env.lastDslContext != null) {
      println "HTML for UI Module" + env.lastUiModule + ": ";
      try {
        env.lastDslContext.getHtmlSource(env.lastUiModule);
      } catch (Exception e) {

      }
    }

    println "Environment: ";
    //dump Environment variables
    println env.toString();

    println "Last Error: ";
    println env.lastErrorDescription;

    if (env.lastDslContext != null) {
      println "System log: ";
      println env.lastDslContext.retrieveLastRemoteControlLogs();
    }

    println "----------------------------    End     --------------------------------"
  }*/

  def showTrace() {
    tracer.report()
  }

  public void warn(String message){
    tracer.warn(message);
  }

  public void log(String message){
    tracer.log(message); 
  }

}