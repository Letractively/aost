package example.test.groovy

import example.google.GoogleStartPage
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import example.google.NewGoogleStartPage

class GoogleStartPageGroovyTestCase extends StandaloneTelluriumGroovyTestCase{

    public void initUi() {
    }

    public void setUp(){
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
        ngsp.doGoogleSearch("tellurium selenium")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium")
    }
}