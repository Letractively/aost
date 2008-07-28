//define google start page
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
    InputBox(uid: "searchbox", clocator: [title: "Google Search"])
    SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
    SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}

//define AOST project menu
ui.Container(uid: "menu", clocator: [tag: "table", id: "mt", trailer: "/tbody/tr/th"], group: "true"){
    //since the actual text is  Project&nbsp;Home, we can use partial match here. Note "%%" stands for partial match
    UrlLink(uid: "project_home", clocator: [text: "%%Home"])
    UrlLink(uid: "downloads", clocator: [text: "Downloads"])
    UrlLink(uid: "wiki", clocator: [text: "Wiki"])
    UrlLink(uid: "issues", clocator: [text: "Issues"])
    UrlLink(uid: "source", clocator: [text: "Source"])
}

//define the AOST project search module, which includes an input box, two search buttons
ui.Form(uid: "search", clocator: [:], group: "true"){
    InputBox(uid: "searchbox", clocator: [name: "q"])
    SubmitButton(uid: "search_project_button", clocator: [value: "Search Projects"])
    SubmitButton(uid: "search_web_button", clocator: [value: "Search the Web"])
}

openUrl "http://www.google.com"
type "google_start_page.searchbox", "Aost Selenium"
pause 500
click "google_start_page.Imfeelinglucky"
pause 1000

openUrl "http://code.google.com/p/aost/"
click "menu.project_home"
pause 1000
click "menu.downloads"
pause 1000
click "menu.wiki"
pause 1000
click "menu.issues"
pause 1000

openUrl "http://code.google.com/p/aost/"
type "search.searchbox", "Aost Selenium"
click "search.search_project_button"
pause 5000

type "search.searchbox", "aost selenium dsl groovy"
click "search.search_web_button"
pause 5000