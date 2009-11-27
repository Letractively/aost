package org.tellurium.example.test.java;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.tellurium.example.other.InternalProfileModule;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: May 14, 2009
 */
public class InternalProfileTestCase extends TelluriumJavaTestCase {
    private static InternalProfileModule ipm;
    @BeforeClass
    public static void initUi() {
        ipm = new InternalProfileModule();
        ipm.defineUi();
        ipm.useJQuerySelector();
    }

    @Before
    public void setUp(){
    }

    @Test
    public void testDump(){
        ipm.dump("Main");
    }

    @Ignore
    @Test
    public void testGetListSize(){
        connectUrl("http://localhost:8080/InternalProfile.html");
        int size = ipm.getListSize("Main.UserEditPrivateProfileForm.Fields");
        System.out.println("Get List Size: " + size);
    }
}
