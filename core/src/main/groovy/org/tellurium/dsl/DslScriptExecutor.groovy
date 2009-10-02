package org.tellurium.dsl

import org.tellurium.i18n.InternationalizationManager;
class DslScriptExecutor {

    public static void main(String[] args){
       if(args != null && args.length == 1){
            def dsl = new File(args[0]).text
            def script = """
                import org.tellurium.dsl.DslScriptEngine
import org.tellurium.i18n.InternationalizationManager
                class DslTest extends DslScriptEngine{
                    def test(){
                        init()
                        ${dsl}
                        shutDown()
                        InternationalizationManager i18nManager = new InternationalizationManager()
                        
                        println i18nManager.translate("DslScriptExecutor.DslTestDone")
                    }
                }

                DslTest instance = new DslTest()
                instance.test()
            """

            new GroovyShell().evaluate(script)

       }else{
    	   InternationalizationManager i18nManager = new InternationalizationManager()
           
           println i18nManager.translate("DslScriptExecutor.Usage")
       }

    }
}