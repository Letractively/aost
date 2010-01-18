package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 10, 2009
 * 
 */

public class HariModule extends DslContext {

    public void defineUi() {
      ui.Table(uid:"dependantsTable", clocator:[class:"striped"], respond:["mouseDown"]){
            RadioButton(uid : "row: *, column:1", clocator:[id:"^dependent"],respond: ["mouseDown"]) // Radio button to select the user
            TextBox(uid: "row:*,column:2") // FirstName
            TextBox(uid:"row:*,column:3") // LastName
            TextBox(uid:"row:*,column:4") // Relation with main member
            TextBox(uid:"row:*,column:5") // Gender
            TextBox(uid:"row:*,column:6") // Age
            TextBox(uid:"row:*,column:7") // DOB
            TextBox(uid:"row:*,column:8") // Photo
        }
    }
}