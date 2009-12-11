package org.telluriumsource.config

import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;


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
    protected Properties props
  
    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl()

    public void parse(String fileName){
       try{
            println i18nManager.translate("TelluriumConfigParser.parseConfigFileText" , fileName)
            conf = new ConfigSlurper().parse(new File(fileName).toURL())
            //convert the ConfigObject to properties to check if it is defined
            props = conf.toProperties()
       }catch(Exception e){
            conf = null
            println i18nManager.translate("TelluriumConfigParser.cannotOpenConfigFile" , e.getMessage())
       }
    }

    public void parse(File file){
       try{
          conf = new ConfigSlurper().parse(file.toURL())
          //convert the ConfigObject to properties to check if it is defined
          props = conf.toProperties()
       }catch(Exception e){
          conf = null
          println "Cannot open Configuration File"
       }
    }

    public void parse(URL url){
       try{
          conf = new ConfigSlurper().parse(url)
           //convert the ConfigObject to properties to check if it is defined
          props = conf.toProperties()
      }catch(Exception e){
          conf = null
          println "Cannot open Configuration File"
       }
    }
}