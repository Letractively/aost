package org.tellurium.bootstrap

import org.tellurium.framework.TelluriumFramework
import org.tellurium.framework.TelluriumFrameworkMetaClass

/**
 * Used to initalize the Tellurium framework
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class TelluriumSupport {

    public static TelluriumFramework addSupport(){
        
       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(TelluriumFramework, new TelluriumFrameworkMetaClass())

       return new TelluriumFramework()
    }

    //Disable embedded selenium server
    public static TelluriumFramework addSupportWithoutEmbeddedSeleniumServer(){

       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(TelluriumFramework, new TelluriumFrameworkMetaClass())

       TelluriumFramework framework = new TelluriumFramework()
       framework.disableEmbeddedSeleniumServer()

       return framework
    }
}