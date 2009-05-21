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

public class Extension implements GroovyInterceptable {

   private CommandBundleProcessor cbp  = CommandBundleProcessor.instance

   def invokeMethod(String name, args) {
     
      return cbp.metaClass.invokeMethod(cbp, name, args)
   }
}