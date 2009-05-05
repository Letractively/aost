package example.google

import org.tellurium.dsl.DslContext

/**
 *
 *   Sample test for Google Books page at:
 *
 *       http://books.google.com/
 *   to demostrate the List UI object
 *
 *   The portion of the HTML markup is shown in the file GoogleBooksList.xml
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class GoogleBooksList extends DslContext{

    public void defineUi() {
      
        ui.Container(uid: "GoogleBooksList", locator: "//table[@id='hp_table']/tbody/tr/td[1]/div/div[1]"){
            TextBox(uid: "category", locator: "/div")
            List(uid: "subcategory", locator: "", separator: "p"){
                UrlLink(uid: "all", locator: "/a")
            }
        }
    }

    String getCategory(){
        getText "GoogleBooksList.category"
    }

    int getListSize(){
        getListSize "GoogleBooksList.subcategory"
    }

    def getAllObjectInList(){
        int size = getListSize()
        List list = new ArrayList()
        for(int i=1; i<=size; i++){
           list.add(getUiElement("GoogleBooksList.subcategory[${i}]"))
        }

        return list
    }

    def clickList(int index){
        click "GoogleBooksList.subcategory[${index}]"
        waitForPageToLoad 30000
    }

    String getText(int index){
        getText "GoogleBooksList.subcategory[${index}]"
    }
}