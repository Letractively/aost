package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Sep 20, 2010
 * 
 */
class DataGridModule extends DslContext{
  
    public void defineUi() {
/*        ui.StandardTable(uid: "HelloFormoutGrid", clocator: [tag: "table", id: "outGrid"], namespace: "helloForm"){
            InputBox(uid: "{tbody: 1, row: all, column: 1}", clocator: [name:"*textCol"], namespace: "helloForm")
            TextBox(uid: "{tbody: 1, row: all, column: 2}", clocator: [tag: "td"], self: true, namespace: "helloForm")
            UrlLink(uid: "{tbody: 1, row: all, column: 3}", clocator: [:], namespace: "helloForm")
            SubmitButton(uid: "{tbody: 1, row: all, column: 4}", clocator: [:], namespace: "helloForm")
        }*/
        ui.StandardTable(uid: "HelloFormoutGrid", clocator: [tag: "table", id: "helloForm:outGrid"]){
            InputBox(uid: "{tbody: 1, row: all, column: 1}", clocator: [name:"*helloForm:textCol"])
            TextBox(uid: "{tbody: 1, row: all, column: 2}", clocator: [tag: "td"], self: true)
            UrlLink(uid: "{tbody: 1, row: all, column: 3}", clocator: [:])
            SubmitButton(uid: "{tbody: 1, row: all, column: 4}", clocator: [:])
        }
        ui.StandardTable(uid: "HelloFormoutGrid2", clocator: [tag: "table", id: "helloForm:outGrid"]){
            InputBox(uid: "{row: all, column: 1}", clocator: [:])
            TextBox(uid: "{row: all, column: 2}", clocator: [tag: "td"], self: true)
            UrlLink(uid: "{row: all, column: 3}", clocator: [:])
            SubmitButton(uid: "{row: all, column: 4}", clocator: [:])
        }
    }

  public void testSubmit1(){
      type "HelloFormoutGrid[1][1][1]", "Hello there"
      println getText("HelloFormoutGrid[1][1][2]")
      click "HelloFormoutGrid[1][1][4]"
      //waitForPageToLoad 30000
      pause 500
  }

  public void testLink1(){
      type "HelloFormoutGrid[1][1][1]", "Hello there"
      println getText("HelloFormoutGrid[1][1][2]")
      click "HelloFormoutGrid[1][1][3]"
      pause 500
  }

  public void testSubmit2(){
      type "HelloFormoutGrid[1][1]", "Hello there"
      println getText("HelloFormoutGrid[1][2]")
      click "HelloFormoutGrid[1][4]"
      //waitForPageToLoad 30000
      pause 500
  }

  public void testLink2(){
      type "HelloFormoutGrid[1][1]", "Hello there"
      println getText("HelloFormoutGrid[1][2]")
      click "HelloFormoutGrid[1][3]"
      pause 500
  }

}
