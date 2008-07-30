package example.test.groovy

import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import example.google.NewGoogleStartPage
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase

/**
 *  Test case for Google start page with new UI definition features such as composite locator and group information
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class NewGoogleStartPageGroovyTestCase  extends StandaloneTelluriumGroovyTestCase{

    protected static NewGoogleStartPage ngsp

    public void initUi() {
        ngsp = new NewGoogleStartPage()
        ngsp.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testGoogleSearch(){
        connectUrl("http://www.google.com")
        ngsp.doGoogleSearch("tellurium selenium")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium")
   }

}