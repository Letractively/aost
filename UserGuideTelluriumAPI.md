(A PDF version of the user guide is available [here](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf))



# Tellurium APIs #

Tellurium APIs include all methods defined in DslContext. The time here is in miliseconds unless otherwise specified.

## DSL Methods ##

Here we list all the available methods that can be used as DSLs in DslContext and their corresponding DSL syntax. Note the id here refers to the UiID in the format of "issueSearch.issueType" and the time units are all in milliseconds if not specified. Be aware, you can only apply the methods to the Ui Object if it has these methods defined.

  * `mouseOver id`: Simulates a user hovering a mouse over the specified element.

  * `mouseOut id`: Simulates a user moving the mouse pointer away from the specified element.

  * `mouseDown id`: Simulates a user pressing the mouse button (without releasing it yet) on the specified element.

  * `click id`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `doubleClick id`: Double clicks on a link, button, checkbox or radio button. If the double click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `clickAt id, coordination`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `check id`: Clicks on a link, button, checkbox or radio button. If the click action causes a new page to load (like a link usually does), call waitForPageToLoad.

  * `uncheck id`: Uncheck a toggle-button (checkbox/radio).

  * `type id, input`: Sets the value of an input field, as though you typed it in.

  * `keyType id, value`: Simulates keystroke events on the specified element, as though you typed the value key-by-key.

  * `typeAndReturn id, input`: Sets the value of an input field followed by `<Return>` key.

  * `clearText id`: Resets input field to an empty value.

  * `select id, optionLocator`: Select an option from a drop-down using an option locator.

  * `selectByLabel id, optionLocator`: Select an option from a drop-down using an option label.

  * `selectByValue id, optionLocator`: Select an option from a drop-down using an option value.

  * `addSelectionByLabel id, optionLocator`: Add a selection to the set of selected options in a multi-select element using an option label.

  * `addSelectionByValue id, optionLocator`: Add a selection to the set of selected options in a multi-select element using an option value.

  * `removeSelectionByLabel id, optionLocator`: Remove a selection from the set of selected options in a multi-select element using an option label.

  * `removeSelectionByValue id, optionLocator`: Remove a selection from the set of selected options in a multi-select element using an option value.

  * `removeAllSelections id`: Unselects all of the selected options in a multi-select element.

  * `pause time`: Suspend the current thread for a specified milliseconds.

  * `submit id, attribute`: Submit the specified form. This is particularly useful for forms without submit buttons, e.g. single-input "Search" forms.

  * `openWindow UID, url`: Opens a popup window (if a window with that ID isn't already open). After opening the window, you'll need to select it using the selectWindow command.

  * `selectWindow UID`: Selects a popup window; once a popup window has been selected, all commands go to that window. To select the main window again, use null as the target.

  * `closeWindow UID`: Close the popup window.

  * `selectMainWindow`: Select the original window, i.e., the Main window.

  * `selectFrame frameName`: Selects a frame within the current window.

  * `selectParentFrameFrom frameName`: Select the parent frame from the frame identified by the "frameName".

  * `selectTopFrameFrom`: Select the main frame from the frame identified by the "frameName".

  * `waitForPopUp UID, timeout`: Waits for a popup window to appear and load up.

  * `waitForPageToLoad timeout`: Waits for a new page to load.

  * `waitForFrameToLoad frameAdddress, timeout`: Waits for a new frame to load.

  * `runScript script`: Creates a new "script" tag in the body of the current test window, and adds the specified text into the body of the command.  Scripts run in this way can often be debugged more easily than scripts executed using Selenium's "getEval" command.  Beware that JS exceptions thrown in these script tags aren't managed by Selenium, so you should probably wrap your script in try/catch blocks if there is any chance that the script will throw an exception.

  * `captureScreenshot filename`: Captures a PNG screenshot to the specified file.

  * `chooseCancelOnNextConfirmation`: By default, Selenium's overridden window.confirm() function will return true, as if the user had manually clicked OK; after running this command, the next call to confirm() will return false, as if the user had clicked Cancel.  Selenium will then resume using the default behavior for future confirmations, automatically returning true (OK) unless/until you explicitly call this command for each confirmation.

  * `chooseOkOnNextConfirmation`: Undo the effect of calling chooseCancelOnNextConfirmation.  Note that Selenium's overridden window.confirm() function will normally automatically return true, as if the user had manually clicked OK, so you shouldn't need to use this command unless for some reason you need to change your mind prior to the next confirmation.  After any confirmation, Selenium will resume using the default behavior for future confirmations, automatically returning true (OK) unless/until you explicitly call chooseCancelOnNextConfirmation for each confirmation.

  * `answerOnNextPrompt(String answer))`: Instructs Selenium to return the specified answer string in response to the next JavaScript prompt [window.prompt()].

  * `goBack`: Simulates the user clicking the "back" button on their browser.

  * `refresh`: Simulates the user clicking the "Refresh" button on their browser.

  * `dragAndDrop(uid, movementsString)`:  Drags an element a certain distance and then drops it.

  * `dragAndDropTo(sourceUid, targetUid)`: Drags an element and drops it on another element.


## Data Access Methods ##

In addition to the above DSLs, Tellurium provides Selenium-compatible data access methods so that you can get data or Ui statuses from the web. The methods are listed here:

  * `String getConsoleInput()`: Gets input from Console.

  * ` String[] getSelectOptions(id)`: Gets all option labels in the specified select drop-down.

  * `String[] getSelectedLabels(id)`: Gets all selected labels in the specified select drop-down.

  * `String getSelectedLabel(id)`: Gets a single selected label in the specified select drop-down.

  * `String[] getSelectedValues(id)`: Gets all selected values in the specified select drop-down.

  * `String getSelectedValue(id)`: Gets a single selected value in the specified select drop-down.

  * `String[] getSelectedIndexes(id)`: Gets all selected indexes in the specified select drop-down.

  * `String getSelectedIndex(id)`: Gets a single selected index in the specified select drop-down.

  * `String[] getSelectedIds(id)`: Gets option element ID for selected option in the specified select element.

  * `String getSelectedId(id)`: Gets a single element ID for selected option in the specified select element.

  * `boolean isSomethingSelected(id)`: Determines whether some option in a drop-down menu is selected.

  * `String waitForText(id, timeout)`: Waits for a text event.

  * `int getTableHeaderColumnNum(id)`: Gets the column header count of a table

  * `int getTableMaxRowNum(id)`: Gets the maximum row count of a table

  * `int getTableMaxColumnNum(id)`: Gets the maximum column count of a table

  * `int getTableFootColumnNum(id)`: Gets the maximum foot column count of a standard table

  * `int getTableMaxTbodyNum(id)`: Gets the maximum tbody count of a standard table

  * `int getTableMaxRowNumForTbody(id, index)`: Gets the maximum row number of the index-th tbody of a standard table

  * `int getTableMaxColumnNumForTbody(id, index)`: Gets the maximum column number of the index-th tbody of a standard table

  * `int getListSize(id)`: Gets the item count of a list

  * `getUiElement(id)`: Gets the UIObject of an element.

  * `boolean isElementPresent(id)`: Verifies that the specified element is somewhere on the page.

  * `boolean isVisible(id)`: Determines if the specified element is visible. An element can be rendered invisible by setting the CSS "visibility" property to "hidden", or the "display" property to "none", either for the element itself or one if its ancestors.  This method will fail if the element is not present.

  * `boolean isChecked(id)`: Gets whether a toggle-button (checkbox/radio) is checked.  Fails if the specified element doesn't exist or isn't a toggle-button.

  * `boolean isDisabled(id)`: Determines if an element is disabled or not

  * `boolean isEnabled(id)`: Determines if an element is enabled or not

  * `boolean waitForElementPresent(id, timeout)`: Wait for the Ui object to be present

  * `boolean waitForElementPresent(id, timeout, step)`: Wait for the Ui object to be present and check the status by step.

  * `boolean waitForCondition(script, timeout)`: Runs the specified JavaScript snippet repeatedly until it evaluates to "true". The snippet may have multiple lines, but only the result of the last line will be considered.

  * `String getText(id)`: Gets the text of an element. This works for any element that contains text. This command uses either the textContent (Mozilla-like browsers) or the innerText (IE-like browsers) of the element, which is the rendered text shown to the user.

  * `String getValue(id)`: Gets the (whitespace-trimmed) value of an input field (or anything else with a value parameter). For checkbox/radio elements, the value will be "on" or "off" depending on whether the element is checked or not.

  * `String getLink(id)`: Get the href of an element.

  * `String getImageSource(id)`: Get the image source element.

  * `String getImageAlt(id)`: Get the image alternative text of an element.

  * `String getImageTitle(id)`: Get the image title of an element.

  * `getAttribute(id, attribute)`: Get an attribute of an element.

  * `getParentAttribute(id, attribute)`: Get an attribute of the parent of an element.

  * `String getBodyText()`: Gets the entire text of the page.

  * `boolean isTextPresent(pattern)`: Verifies that the specified text pattern appears somewhere on the rendered page shown to the user.

  * `boolean isEditable(id)`: Determines whether the specified input element is editable, ie hasn't been disabled. This method will fail if the specified element isn't an input element.

  * `String getHtmlSource()`: Returns the entire HTML source between the opening and closing "html" tags.

  * `String getExpression(expression)`: Returns the specified expression.

  * `getXpathCount(xpath)`: Returns the number of nodes that match the specified xpath, eg. "//table" would give the number of tables.

  * `String getCookie()`: Return all cookies of the current page under test.

  * `boolean isAlertPresent()`: Has an alert occurred?

  * `boolean isPromptPresent()`: Has a prompt occurred?

  * `boolean isConfirmationPresent()`: Has confirm() been called?

  * `String getAlert()`: Retrieves the message of a JavaScript alert generated during the previous action, or fail if there were no alerts.

  * `String getConfirmation()`: Retrieves the message of a JavaScript confirmation dialog generated during the previous action.

  * `String getPrompt()`: Retrieves the message of a JavaScript question prompt dialog generated during the previous action.

  * `String getLocation()`: Gets the absolute URL of the current page.

  * `String getTitle()`: Gets the title of the current page.

  * `String[] getAllButtons()`: Returns the IDs of all buttons on the page.

  * `String[] getAllLinks()`: Returns the IDs of all links on the page.

  * `String[] getAllFields()`: Returns the IDs of all input fields on the page.

  * `String[] getAllWindowIds()`: Returns the IDs of all windows that the browser knows about.

  * `String[] getAllWindowNames()`: Returns the names of all windows that the browser knows about.

  * `String[] getAllWindowTitles()`: Returns the titles of all windows that the browser knows about.

## Test Support DSLs ##

Tellurium defined a set of DSLs to support Tellurium tests. The most often used ones include

  * connectSeleniumServer(): establish a new connection to Selenium server.

  * openUrl(String url): establish a new connection to Selenium server for the given url. The DSL format is:

```
    openUrl url
```

> Example:

```
    openUrl "http://code.google.com/p/aost/"
```

  * connectUrl(String url): use existing connect for the given url. The DSL format is:

```
    connectUrl url
```

> Example:

```
    connectUrl "http://code.google.com/p/aost/" 
```
