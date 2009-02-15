package org.tellurium.test;

import org.tellurium.test.java.TelluriumJavaTestCase;
import org.tellurium.module.GoogleModule;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: jiafan1
 * Date: Feb 14, 2009
 * Time: 7:37:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleTestCase extends TelluriumJavaTestCase{
    private static GoogleModule gm;

    @BeforeClass
    public static void initUi() {
        gm = new GoogleModule();
        gm.defineUi();
    }

    @Test
    public void testDragAndDrop(){
        connectUrl("http://www.google.com");
        gm.dragAndDrop();
    }

    @Test
    public void testDragAndDropTo(){
        connectUrl("http://www.google.com");
        gm.dragAndDropTo();
    }
}
