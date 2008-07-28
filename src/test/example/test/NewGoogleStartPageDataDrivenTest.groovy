package example.test

import org.aost.test.AostDataDrivenTest

/**
 * New syntax for data driven test
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class NewGoogleStartPageDataDrivenTest extends AostDataDrivenTest{

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
        loadData "src/test/example/dsl/newgooglesearchinput.txt"

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