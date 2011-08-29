package org.telluriumsource.module

import org.telluriumsource.dsl.DslContext


class YahooHomePage extends DslContext {

    public void defineUi() {

        // Define UI for left menu of Yahoo Site

        // this worked... using an ID on the li tag


//        ui.Div(uid: "div1", clocator: [tag: "div", id: "u_2588582-sb", class: "sb"]) {
        ui.Div(uid: "div1", clocator: [tag: "div", class: "sb"]) {
            UrlLink(uid: "autos", clocator: [text: "Autos"])
            UrlLink(uid: "dating", clocator: [text: "Dating"])
            UrlLink(uid: "jobs", clocator: [text: "Jobs"])
        }
    }  // end defineUi

    public void navigateAutos() {
        click "div1.autos"
        waitForPageToLoad 250000
    }

    public void navigateDating() {
        click "div1.dating"
        waitForPageToLoad 250000
    }

    public void navigateJobs() {
        click "div1.jobs"
        waitForPageToLoad 250000
    }

}
