package org.telluriumsource.ft

import org.junit.Ignore
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory

/**
 * Functional Test for GoogleDdDslContext
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleDdDslContextTestCase extends GroovyTestCase{

    public void setUp() {
      SessionManager.setSession(MockSessionFactory.getNewSession());
    }

//    @Ignore
    public void testGoogleSearch(){
        GoogleDdDslContext gddc = new GoogleDdDslContext()

        gddc.init()
        gddc.connectSeleniumServer()
        gddc.test()
        gddc.shutDown()
    }
}