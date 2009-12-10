package org.telluriumsource.dsl

import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;

class DslScriptExecutor {

    public static void main(String[] args){
       if(args != null && args.length == 1){
            def dsl = new File(args[0]).text
            def script = """
                import org.telluriumsource.dsl.DslScriptEngine
                import org.telluriumsource.i18n.InternationalizationManager
                class DslTest extends DslScriptEngine{
                    def test(){
                        init()
                        ${dsl}
                        shutDown()
                        InternationalizationManager i18nManager = new InternationalizationManagerImpl()
                        
                        println i18nManager.translate("DslScriptExecutor.DslTestDone")
                    }
                }

                DslTest instance = new DslTest()
                instance.test()
            """

            new GroovyShell().evaluate(script)

       }else{
    	   InternationalizationManager i18nManager = new InternationalizationManagerImpl()
           
           println i18nManager.translate("DslScriptExecutor.Usage")
       }

    }
}