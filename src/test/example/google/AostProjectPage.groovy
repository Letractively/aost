package example.google

import org.tellurium.dsl.DslContext

/**
 *  Sample test for the AOST project page at:
 *
 *      http://code.google.com/p/aost/
 *   to demostrate the List UI object, composite locator, group feature, and multiple UI modules in a single file
 *
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class AostProjectPage extends DslContext{

    public void defineUi() {
        //define the menu
        //It is fine to useString Container to abstract Table if you have special table
        ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
            //since the actual text is  Project&nbsp;Home, we can useString partial match here. Note "%%" stands for partial match
            UrlLink(uid: "project_home", clocator: [text: "%%Home"])
            UrlLink(uid: "downloads", clocator: [text: "Downloads"])
            UrlLink(uid: "wiki", clocator: [text: "Wiki"])
            UrlLink(uid: "issues", clocator: [text: "Issues"])
            UrlLink(uid: "source", clocator: [text: "Source"])
        }

        //define the search module, which includes an input box, two search buttons
        ui.Form(uid: "search", clocator: [:], group: "true"){
            InputBox(uid: "searchbox", clocator: [name: "q"])
            SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
            SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
        }
    }

    def clickProjectHome(){
        click "menu.project_home"
        pause 5000
    }

    def clickDownloads(){
        click "menu.downloads"
        pause 5000
    }

    def clickWiki(){
        click "menu.wiki"
        pause 5000
    }

    def clickIssues(){
        click "menu.issues"
        pause 5000
    }

    def clickSource(){
        click "menu.source"
        pause 5000
    }

    def searchProject(String input){
        type "search.searchbox", input
        click "search.search_project_button"
        pause 5000
    }

    def searchWeb(String input){
        type "search.searchbox", input
        click "search.search_web_button"
        pause 5000
    }
}