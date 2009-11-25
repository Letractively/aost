package org.tellurium.test.groovy

import org.tellurium.bootstrap.TelluriumSupport
import org.tellurium.connector.SeleniumConnector
import org.tellurium.framework.TelluriumFramework
import org.tellurium.test.groovy.BaseTelluriumGroovyTestCase

/**
 * This test case only applies to a standalone test case which will be run by itself. Should not use
 * it if you plan to add the test case to a test suite.
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
abstract class TelluriumGroovyTestCase extends BaseTelluriumGroovyTestCase{

//    protected EmbeddedSeleniumServer server;

    protected SeleniumConnector connector;
    protected TelluriumFramework aost

    public SeleniumConnector getConnector() {
        return connector;
    }

    public void initUi(){

    }

//    @BeforeClass
    protected void setUpForClass() {
        aost = TelluriumSupport.addSupport()
        aost.start(customConfig)
        connector = aost.connector
        initUi()
    }

//    @AfterClass
    protected void tearDownForClass() {
        if(aost != null)
            aost.stop()
    }

}