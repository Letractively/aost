package org.tellurium.extend

import org.tellurium.dispatch.Dispatcher

/**
 * Class to pass user custom methods to the delegator
 *
 * @author: John.Jian.Fang@gmail.com
 *
 * Date: Mar 19, 2009
 *
 */

public class Extension implements GroovyInterceptable{
   def dispatcher  = new Dispatcher()

   def invokeMethod(String name, args) {
     
      return dispatcher.metaClass.invokeMethod(dispatcher, name, args)
    }
}