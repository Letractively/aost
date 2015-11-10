

# Introduction #

[The Rich Web Experience 2009](http://www.therichwebexperience.com/conference/orlando/2009/12/home) was held on December 1-4 in Orlando, FL. Luckily, we got in as [a rapid fire session](http://www.therichwebexperience.com/conference/orlando/2009/12/schedule) from 7:45PM-8:30PM on December 2nd. Even though Tellurium was covered by [Paul King](http://www.springone2gx.com/conference/new_orleans/2009/10/speakers/paul_king), co-author of "Groovy in Action", at [Agile 2009](http://www.slideshare.net/paulk_asert/groovy-testing-aug2009-1945995), it was the first public presentation by our Tellurium team. We were very excited about it.

Vivek and I drove for 10 hours on December 1st to get Orlando, FL from Knoxville, TN. We were exhausted when we arrived in mid night. On the second day, we went to the reception desk to get our badges. After the breakfast, we started to attend sessions. I attended Brian Sletten's two sessions on REST and RDFA in the morning, and Dylan Schiemann's Dojo Toolkit in the afternoon. They were all very good presentations.

After the BIRDS OF A FEATHER SESSION and dinner, Vivek and I started to prepare for the presentation. We first had some problem with the screen resolution for the projector, but soon we resolved it. I brought in a digital camera, but it was a pretty old one still with cassette. I had really a hard time to find a right place to put it since the organizer did not provide any recording service. I put it on a third row chair, but Dylan Schiemann came and sat on the second row in front of it, we would record part of his shoulder if we kept the camera there, thus, I had to move the camera to another chair in a hurry.

We thought there might be only couple people in our session after the long day. Even I felt tired after so many sessions somehow. Surprisingly,  actually there were 15-20 people in our session. Overall, the session was good. Our demos run smoothly. Dylan Schiemann made a suggestion to change the jQuery selector in our slides to CSS selector. He was right, we should use CSS selector because jQuery implements the CSS selectors.

On interesting usage of Tellurium is to package reusable modules as a jar file and then use the modules just like regular Tellurium UI objects. We put a Dojo container widget on the slide, but which was real not a good example. We had a Dojo Date Picker example, but the web site we used for testing was gone, we didn't show it. Otherwise, the Dojo Date Picker example should be more convincing.

[The talk](http://aost.googlecode.com/files/TelluriumPresentation.pdf) was dragged on a bit over one hour and finished with couple questions. From what I saw, people were interested even our demos might not really impress them so deeply. We talked to an architect from State Farm after the session, they were also frustrated with Selenium and came out with some similar ideas like page objects and flow objects to abstract the web UIs. There were couple other sessions on testing such as "Testing the Web Layer" and "Advanced Selenium", which mainly focus on Selenium. Unfortunately, we did not have time to attend the sessions. We believe Tellurium is the right way to do web testing if you want to create robust and maintainable test cases.

As a side note, jQuery was really hot in the conference. We asked couple developers at the conference what would be the most interest thing they want to try after the conference. They said jQuery. I attended a jQuery UI session, they have live demos on their web site (like jQuery), which was really impressive. Tellurium should do in a similar way to smooth the learning curve.

# Demo Code #

We used the book application example from Grails for our demos. The UI modules are as follows.

## Book Menu ##

```


import org.tellurium.dsl.DslContext



/**

 * @author Vivek Mongolu

 */



public class TelluriumBookMenu  extends DslContext {



  public void defineUi() {



    ui.Container(uid: "TopMenu", clocator: [tag: "div", class: "nav"]){

	  UrlLink(uid: "Home", clocator: [tag: "a", text: "Home", href: "/BooksApp", class: "home"])

	  UrlLink(uid: "NewBook", clocator: [tag: "a", text: "New Book", class: "create", href: "/BooksApp/book/create"])

  }

  }



  //Add your methods here

  public void clickHome(){

    click "TopMenu.Home"

    waitForPageToLoad 30000

  }



  public void clickNewBook(){

    click "TopMenu.NewBook"

    waitForPageToLoad 30000

  }



}
```

## Create Book ##

```


import org.tellurium.dsl.DslContext



/**

 * @author Vivek Mongolu

 */



public class TelluriumCreateBookModule  extends DslContext {



  public void defineUi() {



    ui.Form(uid: "CreateForm", clocator: [tag: "form", method: "post", action: "/BooksApp/book/save"]){

      Container(uid: "CreateTable", clocator: [tag: "table"]){

          InputBox(uid: "TitleInput", clocator: [tag: "input", type: "text", name: "title", id: "title"])

          InputBox(uid: "Author", clocator: [tag: "input", type: "text", name: "author", id: "author"])

          InputBox(uid: "PagesInput", clocator: [tag: "input", type: "text", name: "pages", id: "pages"])

          Selector(uid: "CategoryInput", clocator: [tag: "select", id: "category", name: "category"])

      }

      SubmitButton(uid: "CreateButton", clocator: [tag: "input", type: "submit", value: "Create", class: "save"])

    }



    ui.Container(uid: "ShowBook", clocator: [:]){

      Div(uid: "Message", clocator: [tag: "div", class: "message"])

    }



    ui.Container(uid: "ErrorBook", clocator: [:]){

      Div(uid: "Message", clocator: [tag: "div", class: "errors"])

    }

  }



  //Add your methods here

  public void createBook(String author, String  pages, String title, String category) {

    keyType "CreateForm.CreateTable.Author", author

    keyType "CreateForm.CreateTable.PagesInput", pages

    keyType "CreateForm.CreateTable.TitleInput", title

    selectByLabel "CreateForm.CreateTable.CategoryInput", category 

    click "CreateForm.CreateButton"

    waitForPageToLoad 30000

  }
```

## Book List ##

```

import org.tellurium.dsl.DslContext



/**

 * @author Vivek Mongolu

 */



public class TelluriumBookListModule extends DslContext{



  public void defineUi(){

    ui.Container(uid: "BookList", clocator : [tag : "div", class : "list"]){

        Table(uid : "BookListTable", clocator : [:]){

          UrlLink(uid : "row: *, column: 1", clocator : [:])

          TextBox(uid: "all", clocator: [:])

        }

    }

  }



  public String[] getAllText(){

    //return getAllTableCellText("BookList.BookListTable")

    return getAllTableCellTextForTbody("BookList.BookListTable");

  }



   public List<String> getDataForColumn(int column){

        int nrow = getTableMaxRowNum("BookList.BookListTable")

        List<String> list = new ArrayList<String>()

        for(int i=1; i<nrow; i++){

            list.add(getText("BookList.BookListTable[${i}][${column}]"))

        }



        return list

  }



  public String getCellText(int row, int column){

    return getText("BookList.BookListTable[${row}][${column}]")

  }



  public int getRowNum(){

        int nrow = getTableMaxRowNum("BookList.BookListTable")



        return nrow

  }



  public boolean compareCellText(String cellText, int row, int column){

    return cellText?.equals(getCellText(row, column));

  }




  public int getRowNumForCellText(String cellText){

    int nrow = getTableMaxRowNum("BookList.BookListTable");

    int ncols = getTableMaxColumnNum("BookList.BookListTable");

    int rowNum = -1;

    String colText;

    for(int i=1; i<=nrow; ++i){

      for(int k=1; k <= ncols ; ++k){

        colText = getCellText(i, k);

        if(cellText?.equals(colText)){

          rowNum = i;

          break;

        }

      }

    }

    return rowNum;

  }



  public void showBook(int row){

    click "BookList.BookListTable[${row}][1]";

    waitForPageToLoad(30000);

  }


}
```

# Resources #

  * [The Rich Web Experience 2009](http://www.therichwebexperience.com/conference/orlando/2009/12/home)
  * [Tellurium slides at Rich Web Experience 2009](http://aost.googlecode.com/files/TelluriumPresentation.pdf)