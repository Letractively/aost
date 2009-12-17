package org.telluriumsource.test;

import org.telluriumsource.tester.QUnitTestCase;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 16, 2009
 */
public class QUnitDemoTest extends QUnitTestCase {
    @BeforeClass
    public static void addUrl(){
        registerHtmlBody("/demo.html", Demo.js, Demo.body);
        start();
    }

    @Test
    public void testDemo(){
        openUrl("http://localhost:8080/demo.html");
    }
}
