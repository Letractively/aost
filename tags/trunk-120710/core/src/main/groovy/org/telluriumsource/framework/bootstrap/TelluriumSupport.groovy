package org.telluriumsource.framework.bootstrap

import org.telluriumsource.framework.TelluriumFramework

/**
 * Used to initalize the Tellurium framework
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class TelluriumSupport {

    public static TelluriumFramework addSupport(){
      
      TelluriumFramework instance = TelluriumFramework.instance;
      if(!instance.isStarted)
        instance.start();
      return instance;
    }

    //Disable embedded selenium server
    public static TelluriumFramework addSupportWithoutEmbeddedSeleniumServer(){
      TelluriumFramework instance = TelluriumFramework.instance;
      if(!instance.isStarted)
        instance.start();
      instance.runEmbeddedSeleniumServer = false;
      
      return instance;
    }
}