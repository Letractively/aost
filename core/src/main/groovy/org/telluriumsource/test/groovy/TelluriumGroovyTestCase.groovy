package org.telluriumsource.test.groovy

import org.telluriumsource.framework.bootstrap.TelluriumSupport
import org.telluriumsource.component.connector.SeleniumConnector

/**
 * This test case only applies to a standalone test case which will be run by itself. Should not use
 * it if you plan to add the test case to a test suite.
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
abstract class TelluriumGroovyTestCase extends BaseTelluriumGroovyTestCase{

    public SeleniumConnector getConnector() {
        return conn;
    }

    public void initUi(){

    }

//    @BeforeClass
    protected void setUpForClass() {
        tellurium = TelluriumSupport.addSupport()
        tellurium.startServer(customConfig)
        conn = getCurrentConnector()
        initUi()
    }

//    @AfterClass
    protected void tearDownForClass() {
        if(tellurium != null)
            tellurium.stopServer()
    }



}