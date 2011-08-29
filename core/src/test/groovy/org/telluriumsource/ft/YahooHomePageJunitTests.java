package org.telluriumsource.ft;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.telluriumsource.module.AutosPage;
import org.telluriumsource.module.YahooHomePage;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

public class YahooHomePageJunitTests extends TelluriumJUnitTestCase {
   private static YahooHomePage homePage;
   private static AutosPage autoPage;

   @BeforeClass
   public static void initUi() {
       homePage = new YahooHomePage();
       homePage.defineUi();
       autoPage = new AutosPage();
       autoPage.defineUi();
       connectSeleniumServer();
//       useCache(true);
       useTelluriumEngine(true);
//       useClosestMatch(true);
       useCssSelector(true);
   }

   @Before
   public void connect(){
       connectUrl("http://www.yahoo.com");
   }

   @Test
   public void testNavigation(){
       homePage.navigateAutos();
       System.out.println("Navigated to Autos");
       autoPage.returnToParentPage();
       System.out.println("Back on home page");
//       homePage.diagnose("div1.autos");
       homePage.validate("div1");
       homePage.navigateAutos();
       System.out.println("Navigated to Autos");
   }


   @AfterClass
   public static void showUsage(){
       int size = getCacheSize();
       int maxSize = getCacheMaxSize();
       System.out.println("Cache Size: " + size + ", Cache Max Size:" + maxSize);
       System.out.println("Cache Usage: " + getCacheUsage());
   }
}

