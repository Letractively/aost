//define google start page
ui.Container(uid: "google_start_page", clocator: [tag: "td"], group: "true"){
    InputBox(uid: "searchbox", clocator: [title: "Google Search"])
    SubmitButton(uid: "googlesearch", clocator: [name: "btnG", value: "Google Search"])
    SubmitButton(uid: "Imfeelinglucky", clocator: [value: "I'm Feeling Lucky"])
}

openUrl "http://www.google.com"
type "google_start_page.searchbox", "Tellurium Selenium test"
pause 500
click "google_start_page.Imfeelinglucky"
waitForPageToLoad 30000

openUrl "http://www.google.com"
type "google_start_page.searchbox", "Tellurium Selenium test"
pause 500
click "google_start_page.googlesearch"
waitForPageToLoad 30000
