package org.tellurium.test

import org.tellurium.server.EmbeddedSeleniumServer
import org.tellurium.connector.SeleniumConnector
import org.tellurium.bootstrap.AostSupport
import org.tellurium.framework.AostFramework

/**
 * This test case only applies to a standalone test case which will be run by itself. Should not use
 * it if you plan to add the test case to a test suite.
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class StandaloneAostSeleneseTestCase extends AostSeleneseTestCase{

//    protected EmbeddedSeleniumServer server;

    protected SeleniumConnector connector;
    protected AostFramework aost

    public SeleniumConnector getConnector() {
        return connector;
    }

    public void initUi(){

    }

//    @BeforeClass
    protected void setUpForClass() {
        AostFramework aost = AostSupport.addSupport()
        aost.start()
        connector = aost.connector
        initUi()
    }

//    @AfterClass
    protected void tearDownForClass() {
        if(aost != null)
            aost.stop()
    }

}