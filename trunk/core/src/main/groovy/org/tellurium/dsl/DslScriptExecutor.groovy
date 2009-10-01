package org.tellurium.dsl

import org.tellurium.i8n.InternationalizationManager;
class DslScriptExecutor {

    public static void main(String[] args){
       if(args != null && args.length == 1){
            def dsl = new File(args[0]).text
            def script = """
                import org.tellurium.dsl.DslScriptEngine
                import org.tellurium.i8n.InternationalizationManager
                class DslTest extends DslScriptEngine{
                    def test(){
                        init()
                        ${dsl}
                        shutDown()
                        InternationalizationManager i8nManager = new InternationalizationManager()
                        
                        println i8nManager.translate("DslScriptExecutor.DslTestDone")
                    }
                }

                DslTest instance = new DslTest()
                instance.test()
            """

            new GroovyShell().evaluate(script)

       }else{
    	   InternationalizationManager i8nManager = new InternationalizationManager()
           
           println i8nManager.translate("DslScriptExecutor.Usage")
       }

    }
}