package example.google

import org.tellurium.dsl.DslContext
import org.tellurium.object.UiObject

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
        ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"]) {
          List(uid: "subcategory", clocator: [tag: "div", class: "sbr"], separator: "div") {
            Container(uid: "all") {
              TextBox(uid: "title", clocator: [tag: "div", class: "sub_cat_title"])
              List(uid: "links", separator: "p") {
                UrlLink(uid: "all", clocator: [:])
              }
            }
          }
        }

        ui.Container(uid: "NewGoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true"){
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"]){
                Container(uid: "all", clocator: [tag: "p"]){
                   UrlLink(uid: "link", clocator: [:])
                }
            }
        }

        ui.Container(uid: "NGoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true"){
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "p"){
              UrlLink(uid: "all", clocator: [:])
            }
        }
    }                                                                                               

    String getCategory(){
        getText "GoogleBooksList.subcategory[1].title"
    }

    int getBookListSize(){
        getListSize "GoogleBooksList.subcategory[1].links"
    }

    int getSubcategoryListSize(){
        getListSize "GoogleBooksList.subcategory"
    }

    public UiObject getUiObject(String uid){
        return getUiElement(uid)
    }


    public def getSeparatorAttribute(){      
      getParentAttribute "NGoogleBooksList.subcategory[1]", "class"
    }

    def getAllObjectInList(){
        int size = getBookListSize()
        List list = new ArrayList()
        for(int i=1; i<=size; i++){
           list.add(getUiElement("GoogleBooksList.subcategory[1].links[${i}]"))
        }

        return list
    }

    def clickList(int index){
        click "GoogleBooksList.subcategory[1].links[${index}]"
        waitForPageToLoad 30000  
    }

    def clickNewList(int index){
        click "NewGoogleBooksList.subcategory[${index}].link"
        waitForPageToLoad 30000
    }

    String getText(int index){
        getText "GoogleBooksList.subcategory[1].links[${index}]"
    }
}