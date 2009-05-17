package org.tellurium.test

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 2, 2008
 * 
 */
class List1 extends DslContext {

    public void defineUi() {
        /*
           sample UI

          <div id="id3:id6">
            <table id="id3:id7">
               <tbody>
                <tr>
                    <td>
                        <a href="javascript:void(0);">My Label 1</a>
                    </td>
                </tr>
               </tbody>
            </table>
            <div>
                <br>good</br>
            </div>
            <table id="id3:id8">
              <tbody>
                <tr>
                    <td>
                        <a href="javascript:void(0);">My Label 2</a>
                    </td>
                </tr>
              </tbody>
            </table>
            <input title="cool"/>
             <table id="id4:id9">
               <tbody>
                <tr>
                    <td>
                        <a href="javascript:void(0);">My Label 3</a>
                    </td>
                </tr>
               </tbody>
            </table>
        </div>
        */
        ui.List(uid: "sample", clocator: [tag: "div"]){
            Table(uid: "all", clocator: [:]){
                UrlLink(uid: "row: 1, column: 1", clocator: [text: "%%My Label"])
            }
            Container(uid: "2", clocator: [tag: "div"]){
                TextBox(uid: "text", clocator: [tag: "br"])
            }
            InputBox(uid: "4", clocator: [title: "cool"])
        }
    }

    public void defineList() {
      ui.List(uid: "A", clocator: [tag: "table", trailer: "/tbody"], separator: "tr") {
        InputBox(uid: "1", clocator: [:])
        Selector(uid: "2", clocator: [:])
        TextBox(uid: "all", clocator: [tag: "div"])
      }
      
      ui.List(uid: "B", clocator: [tag: "table", trailer: "/tbody/tr"]) {
        InputBox(uid: "1", clocator: [:])
        Selector(uid: "2", clocator: [:])
        TextBox(uid: "all", clocator: [tag: "div"])
      }
    }
}