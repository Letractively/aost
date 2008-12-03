package example.test.ddt

import org.tellurium.test.ddt.TelluriumDataDrivenModule

/**
 * Data Driven module for google book list
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 31, 2008
 *
 */
class GoogleBookListModule extends TelluriumDataDrivenModule{

    public void defineModule() {
        //define UI module
        ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true") {
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "/p") {
                UrlLink(uid: "all", locator: "/a")
            }
        }

        //define file data format
        fs.FieldSet(name: "GoogleBookList", description: "Google Book List") {
            Test(value: "checkBookList")
            Field(name: "category", description: "book category")
            Field(name: "size", type: "int", description: "google book list size ")
        }

        defineTest("checkBookList"){
            def expectedCategory = bind("GoogleBookList.category")
            def expectedSize = bind("GoogleBookList.size")
            openUrl("http://books.google.com/")
            String category = getText("GoogleBooksList.category")
            compareResult(expectedCategory, category)
            //assertEquals("Fiction", category)

            int size = getListSize("GoogleBooksList.subcategory")
            logMessage("Book List Size " + size)
            checkResult(size){
                assertTrue(expectedSize == size)
            }
        }
    }

}