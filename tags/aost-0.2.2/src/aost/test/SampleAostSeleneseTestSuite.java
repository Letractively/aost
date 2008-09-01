package aost.test;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.extensions.TestSetup;
import groovy.util.GroovyTestSuite;
import aost.server.EmbeddedSeleniumServer;
import aost.bootstrap.AostSupport;
import aost.connector.SeleniumConnector;
import aost.framework.AostFramework;

/**
 * The test suite can hold many test cases. We need to common/shared processing here.
 *
 * Leave this class as a template and do not try to extend it
 * 
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
public final class SampleAostSeleneseTestSuite extends TestSuite
{

    protected static String TEST_ROOT = "src/example/test/";

    protected static GroovyTestSuite gsuite;

    protected static TestSuite suite;

    static {
        gsuite = new GroovyTestSuite();
        suite  = new TestSuite();
    }

    protected static AostFramework aost;

//    protected static EmbeddedSeleniumServer server;
//
//    protected static SeleniumConnector connector = new SeleniumConnector();

    //need to override this method to put test cases to the test suite gsuite
    //but unfortunately static method cannot be overridden.   You have to duplicate
    //everyting here and fill in the addTestCases() method.
    //So, just leave this class as a template and do not try to extend it.
    protected static void addTestCases() throws Exception{

    }

    public static Test suite() throws Exception {
        addTestCases();

        TestSetup wrapper = new TestSetup(suite) {

            protected void setUp() {
                oneTimeSetUp();
            }

            protected void tearDown() {
                oneTimeTearDown();
            }
        };

        return wrapper;
    }

    public static void oneTimeSetUp() {
        aost = AostSupport.addSupport();
        aost.start();
    }

    public static void oneTimeTearDown() {
        aost.stop();
    }

}
