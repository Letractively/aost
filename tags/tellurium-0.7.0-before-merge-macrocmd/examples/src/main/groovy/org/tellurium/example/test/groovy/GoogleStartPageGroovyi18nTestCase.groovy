package org.tellurium.example.test.groovy

import org.tellurium.example.google.GoogleStartPage
import org.tellurium.example.google.GoogleStartPagei18n
import org.tellurium.test.groovy.TelluriumGroovyTestCase
import org.tellurium.test.crosscut.TimingDecorator
import org.junit.Test
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Ignore

class GoogleStartPageGroovyi18nTestCase extends TelluriumGroovyTestCase{

    public void initUi() {
    }

//    @BeforeClass
    public void setUp(){
//        setCustomConfig(true, 5555, "*chrome", true, null)
        setUpForClass()
        geti18nManager().setLocale(new Locale("fr" , "FR"))
		geti18nManager().addResourceBundle("MessagesBundle")
    }

//    @AfterClass
    public void tearDown(){
        tearDownForClass()
    }

    @Test
    void testTypeGoogle(){
        //test google start page using composite locators
    	GoogleStartPagei18n ngsp = new GoogleStartPagei18n()
        ngsp.defineUi()
        openUrl("http://www.google.ca/intl/fr/")
        ngsp.doGoogleSearch(geti18nManager().translate("GoogleSearchString"));
        connectUrl("http://www.google.ca/intl/fr/")
        ngsp.doImFeelingLucky(geti18nManager().translate("LuckySearchString"))
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