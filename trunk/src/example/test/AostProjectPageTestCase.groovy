package example.test

import aost.test.StandaloneAostSeleneseTestCase
import example.google.AostProjectPage

/**
 * Sample test for the AOST project page at:
 *
 *      http://code.google.com/p/aost/
 *   to demostrate the List UI object, composite locator, group feature, and multiple UI modules in a single file
 *
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class AostProjectPageTestCase  extends StandaloneAostSeleneseTestCase {

    protected AostProjectPage app

    public void initUi() {
        app = new AostProjectPage()
        app.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testAostProjectPage(){
        connectUrl("http://code.google.com/p/aost/")
        app.clickDownloads()
        app.clickWiki()
        app.clickIssues()
        app.clickSource()

        app.clickProjectHome()
        app.searchProject("aost")
        
        connectUrl("http://code.google.com/p/aost/")
        app.searchWeb("aost selenium dsl groovy")
    }
}