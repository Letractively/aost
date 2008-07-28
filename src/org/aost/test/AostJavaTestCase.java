package org.aost.test;

import org.aost.connector.SeleniumConnector;
import org.aost.framework.AostFramework;
import org.aost.bootstrap.AostSupport;
import org.junit.BeforeClass;
import org.junit.AfterClass;

/**
 * Java TestCase with @BeforeClass and @AfterClass defined
 *
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
public abstract class AostJavaTestCase extends AostBaseTestCase{

    protected static AostFramework aost;

    @BeforeClass
    public static void setUpForClass() {
        AostFramework aost = AostSupport.addSupport();
        aost.start();
        connector = (SeleniumConnector) aost.getProperty("connector");
    }

    @AfterClass
    public static void tearDownForClass() {
        if(aost != null)
            aost.stop();
    }
}
