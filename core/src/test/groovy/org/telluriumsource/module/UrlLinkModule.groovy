package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext

class UrlLinkModule extends DslContext {
    public void defineUi() {
        ui.Table(uid: "tblLink", clocator: [tag: "td", id: "tblLink"]) {
            UrlLink(uid: "{row: 1, column: 1}", clocator: [tag: "a", text: "Link"])
        }
    }

    public void testLink() {
        def targetLink = "tblLink[1][1]"
        println "text: " + getText(targetLink)
        println "html: " + toHTML(targetLink)
        println "href: " + getLink(targetLink)
        click targetLink
        waitForPageToLoad 30000
    }
}
