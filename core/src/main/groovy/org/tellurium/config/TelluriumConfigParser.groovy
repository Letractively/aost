package org.tellurium.config

import org.tellurium.i8n.InternationalizationManager;

/**
 * Parse Tellurium configuration and store the properties to a Hashmap
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 * 
 */
class TelluriumConfigParser {

    protected def conf
    protected InternationalizationManager i8nManager = new InternationalizationManager()

    public void parse(String fileName){
       try{
   	    	i8nManager.createResourceBundle(Locale.getDefault());
            println i8nManager.translate("TelluriumConfigParser.parseConfigFileText" , {fileName})
            conf = new ConfigSlurper().parse(new File(fileName).toURL())
       }catch(Exception e){
            conf = null
            println i8nManager.translate("TelluriumConfigParser.cannotOpenConfigFile" , {e.getMessage()})
       }
    }

}