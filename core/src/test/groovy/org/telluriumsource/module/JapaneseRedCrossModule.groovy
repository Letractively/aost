package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

/**
 * 
 * 
 * @author Jian Fang (jfang@book.com)
 *
 * Date: 6/1/11
 *
 */
class JapaneseRedCrossModule extends DslContext {
    public void defineUi() {
        ui.Container(uid: "A", clocator: [tag: "a", title: "とっさの手当・予防を学びたい"]) {
            Image(uid: "Rollover", clocator: [tag:
            "img", direct: "true", class: "rollover"])
        }
    }

    void clickLink() {
        click "A.Rollover"
        waitForPageToLoad 30000
    }

}
