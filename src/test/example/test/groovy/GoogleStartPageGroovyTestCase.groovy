package example.test.groovy

import example.google.GoogleStartPage
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase
import org.tellurium.test.groovy.StandaloneTelluriumGroovyTestCase

class GoogleStartPageGroovyTestCase extends StandaloneTelluriumGroovyTestCase{

    protected static GoogleStartPage gsp

    public void initUi() {
        gsp = new GoogleStartPage()
        gsp.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

//    @Test
    void testTypeGoogle(){
        connectUrl("http://www.google.com")
        gsp.type("tellurium groovy")
    }

//    public static junit.framework.Test suite(){
//	    return new JUnit4TestAdapter(GoogleStartPageGroovyTestCase.class)
//    }
}