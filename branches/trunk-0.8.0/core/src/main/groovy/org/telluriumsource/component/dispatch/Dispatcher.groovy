package org.telluriumsource.component.dispatch

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.crosscut.trace.DefaultExecutionTracer
import org.telluriumsource.crosscut.trace.ExecutionTracer
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.util.Helper
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.component.connector.CustomSelenium
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.annotation.Provider
import org.telluriumsource.annotation.Inject

@Provider
class Dispatcher implements Configurable {
    public static final String PLACE_HOLDER = "\\?"

    @Inject(name="i18nBundle", lazy=true)
    protected IResourceBundle i18nBundle ;

    private String filenamePattern = "Screenshot?.png";

//    private SeleniumClient sc = new SeleniumClient();
    @Inject
    private RuntimeEnvironment env;

    private CustomSelenium sel;

    private ExecutionTracer tracer = new DefaultExecutionTracer();
  
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
        RuntimeEnvironment env = SessionManager.getSession().getEnv();
        env.setLastError(e);

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
/*   
    if (env.lastDslContext != null) {
      env.lastDslContext.bugReport();
    }*/

  }

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