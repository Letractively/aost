package org.tellurium.test

import org.tellurium.test.groovy.TelluriumGroovyTestCase
import org.junit.Ignore

/**
 * Test class to find the problem for issue 64
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Nov 17, 2008
 * 
 */
class Window_UT extends TelluriumGroovyTestCase {

    public void initUi() {
    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    public void testWaitForPopup(){
        TWindow tw = new TWindow()
        tw.defineUi()
        
 //       tw.waitForPopup()

 //       tw.selectOriginalWindow()

//        tw.selectChildWindow("windowName")
//        tw.openWindow("windowName", "www.google.com")
//      tw.openWindow("moreInfo", "www.google.com")
    }

}