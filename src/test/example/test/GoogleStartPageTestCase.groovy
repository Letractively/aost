package example.test

import example.google.GoogleStartPage
import org.tellurium.test.StandaloneTelluriumTestCase
import org.tellurium.test.StandaloneTelluriumTestCase

class GoogleStartPageTestCase extends StandaloneTelluriumTestCase{

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
//	    return new JUnit4TestAdapter(GoogleStartPageTestCase.class)
//    }
}