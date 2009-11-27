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

    public SeleniumConnector getConnector() {
        return connector;
    }

    public void initUi(){

    }

//    @BeforeClass
    protected void setUpForClass() {
        tellurium = TelluriumSupport.addSupport()
        tellurium.start(customConfig)
        connector = tellurium.connector
        initUi()
    }

//    @AfterClass
    protected void tearDownForClass() {
        if(tellurium != null)
            tellurium.stop()
    }



}