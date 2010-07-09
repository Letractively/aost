import org.telluriumsource.test.java.TelluriumEasybTestCase
import easyb.module.GoogleSearchModule

scenario "User searches for Tellurium on google.com", {

before "start tellurium"
  tellurium = new TelluriumEasybTestCase();
  tellurium.start();
  googleSearch = new GoogleSearchModule();
  googleSearch.defineUi();
  tellurium.connectSeleniumServer();

given "google.com is up",{
  tellurium.connectUrl("http://www.google.com");
}

when "user searches for Tellurium",{
  googleSearch.doGoogleSearch("tellurium test");
}

then "title should be tellurium",{
  googleSearch.getTitle().shouldStartWith "tellurium test";

}

after "stop tellurium"
    tellurium.stop();
}
