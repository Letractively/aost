package org.tellurium.dispatch

import org.tellurium.client.SeleniumClient
import org.tellurium.config.Configurable
import org.tellurium.test.crosscut.DefaultExecutionTracer
import org.tellurium.test.crosscut.ExecutionTracer



//class Dispatcher implements GroovyInterceptable, Configurable {

class Dispatcher implements Configurable {

    public static final String PLACE_HOLDER = "\\?"

    private boolean captureScreenshot = false
    private boolean trace = true
  
    private String filenamePattern = "Screenshot?.png"

    private SeleniumClient sc = new SeleniumClient()
    private ExecutionTracer tracer = new DefaultExecutionTracer()

/*
    def invokeMethod(String name, args) {

    }
*/

    def methodMissing(String name, args) {
      // If this class automatically throws MissingMethodException, then we only need the following one line
      //		return sc.client.metaClass.invokeMethod(this, name, args)

      //sometimes, the selenium client is not singleton ??
      //here reset selenium client to use the new singleton instance which has the client set
      if (sc.client == null || sc.client.getActiveSeleniumSession() == null)
          sc = new SeleniumClient()

      try {
          long beforeTime = System.currentTimeMillis()
          def result = sc.client.getActiveSeleniumSession().metaClass.invokeMethod(sc.client.getActiveSeleniumSession(), name, args)
          long duration = System.currentTimeMillis() - beforeTime
          if(trace)
            tracer.publish(name, beforeTime, duration)

          return result
      } catch (Exception e) {
          if (this.captureScreenshot) {
              long timestamp = System.currentTimeMillis()
              String filename = filenamePattern.replaceFirst(PLACE_HOLDER, "${timestamp}")
              sc.client.getActiveSeleniumSession().captureScreenshot(filename)
              println "Screenshot for exception <<" + e.getMessage() + ">> is saved to file ${filename}"
          }
          throw e
      }
    }

    def showTrace(){
      tracer.report()
    }
}