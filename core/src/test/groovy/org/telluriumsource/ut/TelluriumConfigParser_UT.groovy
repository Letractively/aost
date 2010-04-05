package org.telluriumsource.ut

import org.telluriumsource.framework.config.TelluriumConfigParser

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
        parser.parse("config/TelluriumConfigForExcelReader.groovy")
        def config = parser.getProperty("conf")
        assertNotNull(config)
        assertEquals("4444", config.tellurium.embeddedserver.port)
    }
}