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
		//jqlocator still needs some work
		ui.Link(uid: 'jqLink', jqlocator: "#hp_table p:eq(3)");
		ui.Container(uid: "GoogleBooksList", locator: "//table[@id='hp_table']/tbody/tr/td[1]/div/div[1]"){
            TextBox(uid: "category", locator: "/div")
            List(uid: "subcategory", locator: "", separator: "p"){
                UrlLink(uid: "all", locator: "/a")
            }
        }
    }

	List getJQSelectedLinkTest(){
		getText 'jqLink'
	}

	List getSubcategoryNames(){
		getSelectorText(".sub_cat_title");
	}
	List getDisplayedBookTitles(){
		ArrayList arr = this.getSelectorProperties(".thumbocover", ['alt']);
		List ret = new ArrayList();
		for(HashMap m : arr){
			ret.put(m.get("alt"));
		}
		return ret;
	}
	List getFictionLinks(){
		List l = this.getSelectorFunctionCall(".sub_cat_title","""
			var out = [];
			this.each(function(){
				if(\$(this).text() === "Fiction"){
					var sib = \$(this).siblings();
					sib.each(function(){
						out.push(\$(this).children()[0].href);
					});
				}
			});
			return out;
		""")
		return l;

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