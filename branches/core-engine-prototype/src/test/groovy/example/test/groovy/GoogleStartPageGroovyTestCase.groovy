package example.test.groovy

import example.google.GoogleStartPage
import example.google.NewGoogleStartPage
import org.tellurium.test.groovy.TelluriumGroovyTestCase

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
        //test google start page using Selenium way, i.e., absolute xpath
        GoogleStartPage gsp = new GoogleStartPage()
        gsp.defineUi()
        connectUrl("http://www.google.com")
        gsp.type("tellurium groovy selenium test")

        //test google start page using composite locators
        NewGoogleStartPage ngsp = new NewGoogleStartPage()
        ngsp.defineUi()
        connectUrl("http://www.google.com")
        ngsp.doGoogleSearch("tellurium selenium automated testing")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium groovy dsl")
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