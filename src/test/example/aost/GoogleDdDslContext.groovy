package example.aost

import org.aost.dsl.DdDslContext
import org.aost.dsl.DslScriptEngine

/**
 * sample DdDslContext to demostrate the usage of DdDslContext
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleDdDslContext extends DslScriptEngine{

    public void test(){
        //define google start page
        ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true") {
            InputBox(uid: "searchbox", clocator: [title: "Google Search"])
            SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
            SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
        }

        //define custom data type and its type handler
        typeHandler "phoneNumber", "example.aost.PhoneNumberTypeHandler"

        //define file data format
        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        //load file
        loadData "src/test/example/dsl/googlesearchinput.txt"

        step{
            //bind variables
            boolean regularSearch = bind("regularSearch")
            def phoneNumber = bind("fs4googlesearch.phoneNumber")
            String input = bind("input")

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", input
            pause 500

        }

        stepOver()
        
        //data stepToEnd test assuming the input data format is defined in FieldSet "fs4googlesearch"
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

            pause 1000

            openUrl "http://www.google.com"
            type "google_start_page.searchbox", phoneNumber
            click "google_start_page.Imfeelinglucky"

            pause 1000
        }

        //close file
        closeData()
    }
}