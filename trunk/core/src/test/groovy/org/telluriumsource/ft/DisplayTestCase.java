package org.telluriumsource.ft;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.telluriumsource.module.DisplayModule;
import org.telluriumsource.test.java.TelluriumMockJUnitTestCase;

import static org.junit.Assert.assertFalse;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 12, 2010
 */
public class DisplayTestCase extends TelluriumMockJUnitTestCase {
    private static DisplayModule dm;

    @BeforeClass
    public static void initUi() {
        registerHtml("Display");

        dm = new DisplayModule();
        dm.defineUi();
    }

    @Before
    public void connectToLocal() {
        connect("Display");
    }

    @Test
    public void testIsVisibleForSelenium(){
        useTelluriumEngine(true);
        assertFalse(dm.isVisible("Container1"));
    }

    @Test
    public void testIsVisibleForTellurium(){
        useTelluriumEngine(false);
        assertFalse(dm.isVisible("Container1"));
    }

    @Test
    public void testUiModule(){
        dm.diagnose("name.newArticle.body.discussionTextArea.bodyLabel");
        dm.validate("name");
    }

    @Ignore
    @Test
    public void testWindow(){
        dm.openPopupWindow();
    }

}
