package org.tellurium.extend

import org.tellurium.dispatch.Dispatcher
import org.tellurium.bundle.CommandBundleProcessor

/**
 * Class to pass user custom methods to the delegator
 *
 * @author: John.Jian.Fang@gmail.com
 *
 * Date: Mar 19, 2009
 *
 */

//public class Extension implements GroovyInterceptable {
public class Extension {

   private CommandBundleProcessor cbp  = CommandBundleProcessor.instance

   def methodMissing(String name, args) {
      return cbp.metaClass.invokeMethod(cbp, name, args)
   }

/*
   def invokeMethod(String name, args) {
     
      return cbp.metaClass.invokeMethod(cbp, name, args)
   }*/
}