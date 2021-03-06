package org.telluriumsource.component.dispatch

import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.crosscut.i18n.IResourceBundle;
import org.telluriumsource.crosscut.trace.ExecutionTracer
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.util.Helper
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.annotation.Provider
import org.telluriumsource.annotation.Inject
import org.telluriumsource.component.connector.CustomSelenium

@Provider
class Dispatcher implements Configurable {
    public static final String PLACE_HOLDER = "\\?"

    @Inject(name="i18nBundle", lazy=true)
    protected IResourceBundle i18nBundle ;

    @Inject(name="tellurium.test.exception.filenamePattern")
    private String filenamePattern = "Screenshot?.png";

    @Inject
    private RuntimeEnvironment env;

    @Inject(name="customSelenium")
    private CustomSelenium sel;

    @Inject
    private ExecutionTracer tracer;
  
    public boolean isConnected(){

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
      String apiName = context.getApiName();
      Object[] params = Helper.removeFirst(args);

      try {
        long beforeTime = System.currentTimeMillis()
        def result = sel.metaClass.invokeMethod(sel, name, params)
        long duration = System.currentTimeMillis() - beforeTime
        if (isUseTrace())
          tracer.publish(apiName, beforeTime, duration)

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