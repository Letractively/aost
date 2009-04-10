package org.tellurium.dispatch

import org.tellurium.client.SeleniumClient
import org.tellurium.config.Configurable



class Dispatcher implements GroovyInterceptable, Configurable {
    public static final String PLACE_HOLDER = "\\?"

    private boolean captureScreenshot = false
    private String filenamePattern = "Screenshot?.png"

    private SeleniumClient sc = new SeleniumClient()
//    private SeleniumClient sc

    def invokeMethod(String name, args) {
        // If this class automatically throws MissingMethodException, then we only need the following one line
//		return sc.client.metaClass.invokeMethod(this, name, args)

        //sometimes, the selenium client is not singleton ??
        //here reset selenium client to use the new singleton instance which has the client set
        if (sc.client == null)
            sc = new SeleniumClient()

        try {
            return sc.client.metaClass.invokeMethod(sc.client, name, args)
        } catch (Exception e) {
            if (this.captureScreenshot) {
                long timestamp = System.currentTimeMillis()
                String filename = filenamePattern.replaceFirst(PLACE_HOLDER, "${timestamp}")
                sc.client.captureScreenshot(filename)
                println "Screenshot for exception <<" + e.getMessage() + ">> is saved to file ${filename}"
            }
            throw e
        }
    }

    protected def methodMissing(String name, args) {
       return invokeMethod(name, args)
    }
}