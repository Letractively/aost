package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.MenuModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 7, 2010
 */
public class MenuTestCase extends TelluriumJUnitTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtml("/menu.html", MenuModule.HTML);
        server.start();
        connectSeleniumServer();
        useCssSelector(true);
    }

    @Test
    public void testCSS() {
        MenuModule mm = new MenuModule();
        mm.defineUi();
        connectUrl("http://localhost:8080/menu.html");
        mm.diagnose("categories.body.categoryList[1].cat[1]");
        System.out.println("\nbackground: ");
        String[] backgrounds = mm.getCSS("categories.body.categoryList[1].cat[1]", "background");
        for (String str : backgrounds) {
            System.out.println("\t" + str);
        }
        System.out.println("\ncolor: ");
        String[] colors = mm.getCSS("categories.body.categoryList[1].cat[1]", "color");
        for (String str : colors) {
            System.out.println("\t" + str);
        }
        System.out.println("\nbackground-color");
        String[] backgroundcolors = mm.getCSS("categories.body.categoryList[1].cat[1]", "background-color");   //background-color
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
        backgroundcolors = mm.getCSS("categories.body.categoryList[1].cat[1]", "backgroundColor");   //background-color
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
    }

    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
