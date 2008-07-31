package example.test.ddt

import org.tellurium.test.ddt.TelluriumDataDrivenTest

/**
 * This is an example to show how to write data driven test. The user defines the actions and the input data specify
 * the test data and which action it applies to. This is a real data driven test in concept.
 *
 * This is a push way to run the test. The data file control all the input and the execution flow.
 * 
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class GoogleStartPagePushTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {
        //define google start page
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true") {
            InputBox(uid: "searchbox", clocator: [title: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
        }

        //define file data format
        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Action(name: "searchaction")
            Field(name: "input", description: "input variable")
        }

        //load file
        loadData "src/test/example/test/ddt/googlesearchpushinput.txt"

        defineAction("search"){

            //bind variable
            String input = bind("input")

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            click "google_start_page.googlesearch"
            pause 1000
        }

        defineAction("Imfeelinglucky"){
            //bind variable
            String input = bind("input")

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            click "google_start_page.Imfeelinglucky"
            pause 1000
        }

        //read one line of data and execute the action
        step()

        //read one line of data and skip the execution
        stepOver()

        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()
    }

}