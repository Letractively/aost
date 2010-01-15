package org.telluriumsource.example.test.java;

import org.telluriumsource.test.java.TelluriumJUnitTestCase;
import org.telluriumsource.test.mock.MockHttpServer;
import org.telluriumsource.example.other.MenuModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 7, 2010
 */
public class MenuTestCase extends TelluriumJUnitTestCase {
    private static MockHttpServer server;
    private static MenuModule mm;
    @BeforeClass
    public static void setUp(){
        mm = new MenuModule();
        mm.defineUi();
        server = new MockHttpServer(8080);
        server.registerHtml("/menu.html", MenuModule.HTML);
        server.start();
        connectSeleniumServer();
        useCssSelector(true);
        useCache(true);
    }

    @Before
    public void connect(){
        connectUrl("http://localhost:8080/menu.html");
    }

    @Test
    public void testCSS() {
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
        String[] backgroundcolors = mm.getCSS("categories.body.categoryList[1].cat[1]", "background-color");  
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
        backgroundcolors = mm.getCSS("categories.body.categoryList[1].cat[1]", "backgroundColor");
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
    }

    @Test
    public void testColors(){
        System.out.println("\nbackground-color for colors.color1");
        String[] backgroundcolors = mm.getCSS("colors.color1", "background-color");
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
        System.out.println("\nbackground-color for colors.color2");
        backgroundcolors = mm.getCSS("colors.color2", "background-color");
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
        System.out.println("\nbackground-color for colors.color3");
        backgroundcolors = mm.getCSS("colors.color3", "background-color");
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }      
        System.out.println("\nbackground-color for colors.color4");
        backgroundcolors = mm.getCSS("colors.color4", "background-color");
        for (String str : backgroundcolors) {
            System.out.println("\t" + str);
        }
    }

    @Test
    public void testDirectCall(){
/*
    var $e = teJQuery("#selenium_myiframe").contents().find("#category-list > li.division:eq(0) ul > li:eq(0)");
    fbLog("SUV", $e);
    $e.css("background-color");
    var elem = $e.get(0);
    fbLog("elem background color", elem.style.background-color);
*/
        String color = (String) mm.customDirectCall("getCSS", "jquery=#category-list > li.division:eq(0) ul > li:eq(0)", "background-color");
        System.out.println("background color: " + color);
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();
    }
}
