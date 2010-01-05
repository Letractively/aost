package org.telluriumsource.test

import org.telluriumsource.config.TelluriumConfigParser

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 2, 2008
 *
 */
class TelluriumConfigParser_UT extends GroovyTestCase{

    public void testParse(){
        TelluriumConfigParser parser = new TelluriumConfigParser()
        parser.parse(ClassLoader.getSystemResource("config/TelluriumConfigForExcelReader.groovy").getFile())
        def config = parser.getProperty("conf")
        assertNotNull(config)
        assertEquals("4444", config.tellurium.embeddedserver.port)
    }

    

}