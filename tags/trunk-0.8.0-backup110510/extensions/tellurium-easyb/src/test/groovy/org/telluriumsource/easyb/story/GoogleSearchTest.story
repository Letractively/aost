import org.telluriumsource.test.java.TelluriumEasybTestCase
import org.telluriumsource.easyb.module.GoogleSearchModule

scenario "user searches for tellurium on google.com", {

before "start tellurium"
  tellurium = new TelluriumEasybTestCase();
  tellurium.start();
  googleSearch = new GoogleSearchModule();
  googleSearch.defineUi();
  tellurium.connectSeleniumServer();

given "google.com is up",{
  tellurium.connectUrl("http://www.google.com");
}

when "user searches for tellurium",{
  googleSearch.doGoogleSearch("tellurium test");
}

then "title should start with tellurium",{
  googleSearch.getTitle().shouldStartWith "tellurium";
}

after "stop tellurium"
    tellurium.stop();
}
