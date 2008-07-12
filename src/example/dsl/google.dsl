ui.Container(uid: "google_start_page"){
    InputBox(uid: "inputbox1", locator: "//input[@title='Google Search']")
    Button(uid: "button1", locator: "//input[@name='btnG' and @type='submit']")
}

openUrl "http://www.google.com"
type "inputbox1", "Cobrakai"
pause 500
click "button1"
pause 1000
