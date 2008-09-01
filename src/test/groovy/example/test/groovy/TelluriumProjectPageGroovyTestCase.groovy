package example.test.groovy

import org.tellurium.test.groovy.TelluriumGroovyTestCase
import example.tellurium.TelluriumProjectPage

/**
 * Sample test for the Tellurium project page at:
 *
 *      http://code.google.com/p/aost/
 *   to demostrate the List UI object, composite locator, group feature, and multiple UI modules in a single file
 *
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class TelluriumProjectPageGroovyTestCase  extends TelluriumGroovyTestCase {

    protected TelluriumProjectPage app

    public void initUi() {
        app = new TelluriumProjectPage()
        app.defineUi()

    }

    public void setUp(){
        setUpForClass()
    }

    public void tearDown(){
        tearDownForClass()
    }

    void testTelluriumProjectPage(){
        connectUrl("http://code.google.com/p/aost/")
        app.clickDownloads()
        app.clickWiki()
        app.clickIssues()
        app.clickSource()

        app.clickProjectHome()
        app.searchProject("tellurium")
        
        connectUrl("http://code.google.com/p/aost/")
        app.searchWeb("tellurium selenium dsl groovy")
    }
}