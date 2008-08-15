package org.tellurium.config
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

    public void parse(String fileName){
       try{
            println "Parse configuration file: ${fileName}"
            conf = new ConfigSlurper().parse(new File(fileName).toURL())
       }catch(Exception e){
            conf = null
            println "Cannot open configuration file ${fileName}: \n" + e.getMessage() + "\n"
       }
    }

}