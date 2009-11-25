package org.tellurium.example.test.ddt

import org.tellurium.config.TelluriumConfigurator;
import org.tellurium.test.ddt.TelluriumDataDrivenTest;

/**
 * example of writing Data Driven test directly in Groovy
 *
 * This example shows how the user can manually control the test flow by specifying the
 * test script in the step() or stepToEnd() method.
 *
 * This is very much like a pull method. You pull data from the file and you have the control
 * of the execution flow and which row of data maps to which field set
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleStartPagePullFromExcelTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {
        //define google start page
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true") {
            InputBox(uid: "searchbox", clocator: [title: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
        }

        //define custom data type and its type handler
        typeHandler "phoneNumber", "org.tellurium.example.handler.PhoneNumberTypeHandler"

        //define file data format
        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        //load file
        getConnector().connectSeleniumServer()
        
         //in order to test excel file we specifically set the data provider
        //usually you can set this in the TelluriumConfig.groovy and we need not do 
        //this explicitly
        TelluriumConfigurator telluriumConfigurator = new TelluriumConfigurator()
        telluriumConfigurator.parse(ClassLoader.getSystemResource("config/TelluriumConfigForExcelReader.groovy").getFile())
        
        loadData "src/main/resources/org/tellurium/example/dsl/test/ddt/GoogleStartPagePullFromExcelTest.xls"

        step{
            //bind variables
            boolean regularSearch = bind("regularSearch")
            def phoneNumber = bind("fs4googlesearch.phoneNumber")
            String input = bind("input")

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            pause 500
            click "google_start_page.googlesearch"
            waitForPageToLoad 30000
        }

        stepOver()

        //read each line and run the test script until the end of the file
        stepToEnd {
            //bind variables
            boolean regularSearch = bind("regularSearch")
            def phoneNumber = bind("fs4googlesearch.phoneNumber")
            String input = bind("input")

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            pause 500

            if (regularSearch)
                click "google_start_page.googlesearch"
            else
                click "google_start_page.Imfeelinglucky"

            waitForPageToLoad 30000


            openUrl "http://www.google.com"
            type "google_start_page.searchbox", phoneNumber
            click "google_start_page.Imfeelinglucky"

            waitForPageToLoad 30000
        }

        getConnector().disconnectSeleniumServer()
        //close file
        closeData()
    }
}