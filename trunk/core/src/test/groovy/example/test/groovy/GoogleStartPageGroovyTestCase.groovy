package example.test.groovy

import example.google.GoogleStartPage
import example.google.NewGoogleStartPage
import org.tellurium.test.groovy.TelluriumGroovyTestCase
import org.tellurium.test.crosscut.TimingDecorator

class GoogleStartPageGroovyTestCase extends TelluriumGroovyTestCase{

    public void initUi() {
    }

    public void setUp(){
        setCustomConfig(true, 5555, "*chrome", true, null)
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testTypeGoogle(){
        //test google start page using composite locators
        NewGoogleStartPage ngsp = new NewGoogleStartPage()
        ngsp.defineUi()
        connectUrl("http://www.google.com")
        ngsp.doGoogleSearch("tellurium selenium automated testing")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium groovy dsl")
    }

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

    //test the dynamically added event "click"
    void testClick(){
         //test google start page using composite locators
        NewGoogleStartPage ngsp = new NewGoogleStartPage()
        ngsp.defineUi()
        connectUrl("http://www.google.com")
        ngsp.testClick()       
    }
}