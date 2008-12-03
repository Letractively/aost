package org.tellurium.test.groovy

import org.tellurium.connector.SeleniumConnector

/**
 * This test case is supposed to be one part of a test suite and there are dependency between test cases.
 * They will share a same connector
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
abstract class TelluriumSuiteGroovyTestCase extends BaseTelluriumGroovyTestCase {
//     abstract protected void initalization()
//
//     protected void setUp() {
//
//       //Since SeleniumConnector becomes a Singleton class, should get back a shared connector here
//       connector = new SeleniumConnector()
//
//       //extra initalization here
//       initalization()
//     }

//    protected static SeleniumConnector connector  = new SeleniumConnector()

//    protected static void initalization(){
//    }

/*
 //   @BeforeClass
    protected static void setUpForClass(){
        connector = new SeleniumConnector()
        initalization()
    }
*/
    protected SeleniumConnector connector;

    public SeleniumConnector getConnector(){
        if(connector == null)
            return new SeleniumConnector()
        else
            return connector
    }
}