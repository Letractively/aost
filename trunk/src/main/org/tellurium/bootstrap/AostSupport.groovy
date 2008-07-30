package org.tellurium.bootstrap

import org.tellurium.framework.AostFramework
import org.tellurium.framework.AostFrameworkMetaClass

/**
 * Used to initalize the AOST framework
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class AostSupport {

    public static AostFramework addSupport(){
        
       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(AostFramework, new AostFrameworkMetaClass())

       return new AostFramework()
    }

    //Disable embedded selenium server
    public static AostFramework addSupportWithoutEmbeddedSeleniumServer(){

       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(AostFramework, new AostFrameworkMetaClass())

       AostFramework framework = new AostFramework()
       framework.disableEmbeddedSeleniumServer()

       return framework
    }
}