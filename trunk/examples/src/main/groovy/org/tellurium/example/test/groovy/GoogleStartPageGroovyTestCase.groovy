package org.tellurium.example.test.groovy

import org.tellurium.example.google.GoogleStartPage
import org.tellurium.example.google.NewGoogleStartPage
import org.tellurium.test.groovy.TelluriumGroovyTestCase
import org.tellurium.test.crosscut.TimingDecorator
import org.junit.Test
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Ignore

class GoogleStartPageGroovyTestCase extends TelluriumGroovyTestCase{

    public void initUi() {
    }

//    @BeforeClass
    public void setUp(){
//        setCustomConfig(true, 5555, "*chrome", true, null)
        setUpForClass()
    }

//    @AfterClass
    public void tearDown(){
        tearDownForClass()
    }

    @Test
    void testTypeGoogle(){
        //test google start page using composite locators
        NewGoogleStartPage ngsp = new NewGoogleStartPage()
        ngsp.defineUi()
        openUrl("http://www.google.com")
//        connectUrl("http://www.google.com")
        ngsp.doGoogleSearch("tellurium selenium automated testing")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium groovy dsl")
    }

/*
    @Ignore
    @Test
    void testTimingDecorator(){
      //test google start page using Selenium way, i.e., absolute xpath
      TimingDecorator decorator = new TimingDecorator( new GoogleStartPage())
      decorator.setWhiteList(["type"])
      decorator.defineUi()
      connectUrl("http://www.google.com")
      decorator.type("tellurium groovy selenium test")
      decorator.storeResult("testTimingDecorator", 1, "Test")
      decorator.outputResult()
    }
*/

/*    @Test
    //test the dynamically added event "click"
    void testClick(){
         //test google start page using composite locators
        NewGoogleStartPage ngsp = new NewGoogleStartPage()
        ngsp.defineUi()
        connectUrl("http://www.google.com")
        ngsp.testClick()       
    }*/
}