package aost.bootstrap

import aost.framework.AostFramework
import aost.framework.AostFrameworkMetaClass

/**
 * Used to initalize the AOST framework
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class AostSupport {

    public static AostFramework addSupport(){
        
       def registry = GroovySystem.metaClassRegistry

       registry.setMetaClass(AostFramework, new AostFrameworkMetaClass())

       return new AostFramework()
    }
}