package org.tellurium.dispatch

import org.tellurium.client.SeleniumClient

class Dispatcher implements GroovyInterceptable{

    private SeleniumClient sc  = new SeleniumClient()
//    private SeleniumClient sc
    
    def invokeMethod(String name, args)
	  {
		// If this class automatically throws MissingMethodException, then we only need the following one line
//		return sc.client.metaClass.invokeMethod(this, name, args)
          
       //sometimes, the selenium client is not singleton ??
       //here reset selenium client to useString the new singleton instance which has the client set
       if(sc.client == null)
          sc = new SeleniumClient()

       return sc.client.metaClass.invokeMethod(sc.client, name, args)
//        return sc.client.metaClass.invokeMethod(name, args)
      }

}