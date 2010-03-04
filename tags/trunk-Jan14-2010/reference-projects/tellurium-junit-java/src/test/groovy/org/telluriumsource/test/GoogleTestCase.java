package org.telluriumsource.test;

import org.telluriumsource.test.java.TelluriumJavaTestCase;
import org.telluriumsource.module.GoogleModule;
import org.junit.BeforeClass;
import org.junit.Test;


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