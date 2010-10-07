package org.telluriumsource.component.custom

import org.telluriumsource.component.bundle.BundleProcessor
import org.telluriumsource.annotation.Provider
import org.telluriumsource.annotation.Inject

/**
 * Class to pass user custom methods to the delegator
 *
 * @author John.Jian.Fang@gmail.com
 *
 * Date: Mar 19, 2009
 *
 */

@Provider
public class Extension {

   @Inject
   private BundleProcessor cbp

   def methodMissing(String name, args) {
      return cbp.metaClass.invokeMethod(cbp, name, args)
   }
}
