package org.telluriumsource.ft

import org.junit.Ignore

/**
 * Functional Test for GoogleDdDslContext
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleDdDslContext_UT extends GroovyTestCase{

    @Ignore
    public void testGoogleSearch(){
        GoogleDdDslContext gddc = new GoogleDdDslContext()
        gddc.init()
        gddc.connectSeleniumServer()
        gddc.test()
        gddc.shutDown()
    }
}