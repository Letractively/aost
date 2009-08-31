package org.tellurium.access

import com.thoughtworks.selenium.SeleniumException
import org.tellurium.config.Configurable
import org.tellurium.dispatch.Dispatcher
import org.tellurium.exception.ElementNotPresentException
import org.tellurium.util.Helper

class Accessor implements Configurable{

    protected static final int ACCESS_WAIT_TIME = 50

    private static final String DISABLED_ATTRIBUTE = "@disabled"

    protected static final String ELEMENT_NOT_PRESENT_ERROR_MESSAGE = "Element is not present"

    def dispatcher  = new Dispatcher()

    private boolean checkElement = true

    public void mustCheckElement(){
        this.checkElement = true
    }

    public void notCheckElement(){
        this.checkElement = false
    }

    protected void checkElement(String locator){
		if(checkElement && (!dispatcher.isElementPresent(locator))){
			waitForElementPresent(locator, ACCESS_WAIT_TIME)
		}
    }

    def boolean isElementPresent(String locator){

		return dispatcher.isElementPresent(locator)
	}

    def boolean isVisible(String locator){

    	return dispatcher.isVisible(locator)
    }

	def boolean isChecked(String locator) {
        checkElement(locator)

		return dispatcher.isChecked(locator)
	}

    def boolean isDisabled(String locator){

        checkElement(locator)

        locator +=DISABLED_ATTRIBUTE 
        return (getAttribute(locator) != null && (getAttribute(locator) == "true"|| getAttribute(locator) == "disabled"));
    }

   def boolean waitForElementPresent(String locator, int timeout){

		//boolean result = false

        for (int second = 0; second < timeout; second+=500) {
            try {
            	if (dispatcher.isElementPresent(locator)){
            		//result = true
            		return true
            		}
            }catch (Exception e){

            }

            Helper.pause(500)
        }

        //return result;
        return false
    }

    def boolean waitForElementPresent(String locator, int timeout, int step){

		//boolean result = false

        for (int second = 0; second < timeout; second += step) {
            try {
            	if (dispatcher.isElementPresent(locator)){
            		//result = true
            		return true
                }
            }catch (Exception e){

            }

            Helper.pause(step)
        }

        //return result;
        return false
    }

	boolean waitForCondition(String script, String timeoutInMilliSecond){

		boolean result = true

		try {
			dispatcher.waitForCondition(script, timeoutInMilliSecond)
		} catch (Exception e) {
			result = false
		}

		return result
	}

	def String getText(String locator){

        checkElement(locator)

    	return dispatcher.getText(locator)
	}

	def String getValue(String locator){

        checkElement(locator)

        return dispatcher.getValue(locator)
	}

    def String[] getSelectOptions(String locator){
        checkElement(locator)

        return dispatcher.getSelectOptions(locator)
    }

    String[] getSelectedLabels(String locator){
        checkElement(locator)

        return dispatcher.getSelectedLabels(locator)
    }

    String getSelectedLabel(String locator){
        checkElement(locator)

        return dispatcher.getSelectedLabel(locator)
    }

    String[] getSelectedValues(String locator){
        checkElement(locator)

        return dispatcher.getSelectedValues(locator)
    }

    String getSelectedValue(String locator){
        checkElement(locator)

        return dispatcher.getSelectedValue(locator)
    }

    String[] getSelectedIndexes(String locator){
        checkElement(locator)

        return dispatcher.getSelectedIndexes(locator)
    }

    String getSelectedIndex(String locator){
        checkElement(locator)

        return dispatcher.getSelectedIndex(locator)
    }

    String[] getSelectedIds(String locator){
        checkElement(locator)

        return dispatcher.getSelectedIds(locator)
    }

    String getSelectedId(String locator){
        checkElement(locator)

        return dispatcher.getSelectedId(locator)
    }

    boolean isSomethingSelected(String locator){
        checkElement(locator)

        return dispatcher.isSomethingSelected(locator)
    }

    String getAttribute(String locator){

      return dispatcher.getAttribute(locator)
    }

    void waitForPopUp(String windowID, String timeout){
        dispatcher.waitForPopUp(windowID, timeout)
    }

    String getBodyText(){
        return dispatcher.getBodyText()
    }

    boolean isTextPresent(String pattern){
       return dispatcher.isTextPresent(pattern)
    }

    boolean isEditable(String locator){
        return dispatcher.isEditable(locator)
    }

    String getHtmlSource(){
        return dispatcher.getHtmlSource()
    }

    String getExpression(String expression){
        return dispatcher.getExpression(expression)
    }

    Number getXpathCount(String xpath){
        return dispatcher.getXpathCount(xpath)
    }

/*
    String getCookie(){
        return dispatcher.getCookie()
    }
*/

    void runScript(String script){
        dispatcher.runScript(script)
    }

    void captureScreenshot(String filename){
        dispatcher.captureScreenshot(filename)
    }

    boolean isAlertPresent(){
        return dispatcher.isAlertPresent()
    }

    boolean isPromptPresent(){
        return dispatcher.isPromptPresent()
    }

    boolean isConfirmationPresent(){
        return dispatcher.isConfirmationPresent()
    }

    String getAlert(){
        return dispatcher.getAlert()
    }

    String getConfirmation(){
        return dispatcher.getConfirmation()
    }

    String getPrompt(){
        return dispatcher.getPrompt()
    }

    String getLocation(){
        return dispatcher.getLocation()
    }

    String getTitle(){
        return dispatcher.getTitle()
    }

    String[] getAllButtons(){
       return dispatcher.getAllButtons()
    }

    String[] getAllLinks(){
       return dispatcher.getAllLinks()
    }

    String[] getAllFields(){
        return dispatcher.getAllFields() 
    }

    String[] getAllWindowIds(){
        return dispatcher.getAllWindowIds()
    }

    String[] getAllWindowNames(){
        return dispatcher.getAllWindowNames()
    }

    String[] getAllWindowTitles(){
        return dispatcher.getAllWindowTitles()
    }

    void waitForPageToLoad(String timeout){
         dispatcher.waitForPageToLoad(timeout)
    }

    void waitForFrameToLoad(String frameAddress, String timeout){
        dispatcher.waitForFrameToLoad(frameAddress, timeout)
    }

    String getEval(String script){
        return dispatcher.getEval(script)
    }

    boolean getWhetherThisFrameMatchFrameExpression(String currentFrameString, String target){
        return dispatcher.getWhetherThisFrameMatchFrameExpression(currentFrameString, target)
    }

    boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString,String target){
        return dispatcher.getWhetherThisWindowMatchWindowExpression(currentWindowString, target)
    }

    void useXpathLibrary(String libraryName){
        dispatcher.useXpathLibrary(libraryName)
    }

    String waitForText(String locator, int timeout){
       waitForElementPresent(locator, timeout)

       return dispatcher.getText(locator)
	}

    String captureNetworkTraffic(String type){
      return dispatcher.captureNetworkTraffic(type)
    }

    void addCustomRequestHeader(String key, String value){
      dispatcher.addCustomRequestHeader(key, value)
    }

  void setTimeout(String timeout) {
     dispatcher.setTimeout(timeout)
  }

  boolean isCookiePresent(String name) {
    return dispatcher.isCookiePresent(name)
  }

  String getCookie() {
    return dispatcher.getCookie()
  }

  String getCookieByName(String name) {
    return dispatcher.getCookieByName(name)
  }

  void createCookie(String nameValuePair, String optionsString) {
    dispatcher.createCookie(nameValuePair, optionsString)
  }

  void deleteCookie(String name, String optionsString) {
    dispatcher.deleteCookie(name, optionsString)
  }

  void deleteAllVisibleCookies() {
    dispatcher.deleteAllVisibleCookies()
  }
}