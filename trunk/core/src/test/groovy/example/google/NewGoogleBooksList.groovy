package example.google

import org.tellurium.dsl.DslContext

/**
 *
 *   Sample test for Google Books page at:
 *
 *       http://books.google.com/
 *   to demostrate the List UI object, composite locator, and group feature
 *
 *   The portion of the HTML markup is shown in the file GoogleBooksList.xml
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 * 
 *
 */
class NewGoogleBooksList extends DslContext{

    public void defineUi() {
        //It is fine to use container for Table if you really do not care what the rows and columns of the elements it holds
        ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true"){
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class:"sub_cat_section"], separator: "/p"){
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