package org.telluriumsource.example.test.groovy

import org.telluriumsource.example.google.NewGoogleStartPage
import org.telluriumsource.test.groovy.TelluriumGroovyTestNGTestCase
import org.testng.TestListenerAdapter
import org.testng.TestNG
import org.testng.annotations.Test

/**
 *
   Example to demonstrate the usage of Tellurium GroovyTestNGTestCase
 *
 * @author John.Jian.Fang@gmail.com
 * Date: Mar 9, 2009
 *
 */

public class GoogleStartPageGroovyTestNGTestCase extends TelluriumGroovyTestNGTestCase{
  NewGoogleStartPage ngsp

  public void initUi() {
    //initialize UI modules here
    ngsp = new NewGoogleStartPage()
    ngsp.defineUi()
    connectSeleniumServer();
  }

  @Test
  void testTypeGoogle(){
        //test google start page using composite locators
        connectUrl("http://www.google.com")
        ngsp.doGoogleSearch("tellurium selenium automated testing")
        connectUrl("http://www.google.com")
        ngsp.doImFeelingLucky("tellurium selenium groovy dsl")
    }

    //test the dynamically added event "click"
    @Test
    void testClick(){

//        ngsp.getConsoleInput()

      //test google start page using composite locators
        connectUrl("http://www.google.com")
        ngsp.testClick()
    }

  static {
    def testng = new TestNG()
    testng.setTestClasses(org.telluriumsource.example.test.groovy.GoogleStartPageGroovyTestNGTestCase)
    testng.addListener(new TestListenerAdapter())
//    testng.run()
  }

}
