package org.tellurium.access

import org.tellurium.util.Helper
import org.tellurium.dispatch.Dispatcher
import com.thoughtworks.selenium.SeleniumException

class Accessor{

    protected static final int ACCESS_WAIT_TIME = 50

    private static final String DISABLED_ATTRIBUTE = "@disabled"

    def dispatcher  = new Dispatcher()
//    def Dispatcher dispatcher
    
    def boolean isElementPresent(String locator){

		return dispatcher.isElementPresent(locator)
	}

    def boolean isVisible(String locator){

    	return dispatcher.isVisible(locator)
    }

	def boolean isChecked(String locator) {
		if(!dispatcher.isElementPresent(locator)){
			waitForElementPresent(locator, ACCESS_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			return dispatcher.isChecked(locator)
		}

		throw new RuntimeException("Check Box " + locator + " is not visible")
	}

    def boolean isDisabled(String locator){
        if(!dispatcher.isElementPresent(locator)){
			waitForElementPresent(locator, ACCESS_WAIT_TIME)
		}

        locator +=DISABLED_ATTRIBUTE 
        return (getAttribute(locator) != null && (getAttribute(locator) == "true"|| getAttribute(locator) == "disabled"));
    }

   def boolean waitForElementPresent(String locator, int timeout){

		//boolean result = false

        for (int second = 0; second < timeout; second+=1000) {
            try {
            	if (dispatcher.isElementPresent(locator)){
            		//result = true
            		return true
            		}
            }catch (Exception e){

            }

            Helper.pause(1000)
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
		String text = null

        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }
    	if (dispatcher.isElementPresent(locator)){
    		text = dispatcher.getText(locator)
    	}

    	return text
	}

	def String getValue(String locator){
		String text = null

        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }
    	if (dispatcher.isElementPresent(locator)){
    		text = dispatcher.getValue(locator)
    	}

    	return text
	}

    def String[] getSelectOptions(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectOptions(locator)
        }

        return null
    }

    String[] getSelectedLabels(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedLabels(locator)
        }

        return null
    }

    String getSelectedLabel(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedLabel(locator)
        }

        return null
    }

    String[] getSelectedValues(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedValues(locator)
        }

        return null
    }

    String getSelectedValue(String locator){
         if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedValue(locator)
        }

        return null
    }

    String[] getSelectedIndexes(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedIndexes(locator)
        }

        return null
    }

    String getSelectedIndex(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedIndex(locator)
        }

        return null
    }

    String[] getSelectedIds(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedIds(locator)
        }

        return null
    }

    String getSelectedId(String selectLocator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectedId(locator)
        }

        return null
    }

    boolean isSomethingSelected(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.isSomethingSelected(locator)
        }

        return false
    }

    String getAttribute(String locator){
        String value
        try{
            value = dispatcher.getAttribute(locator)
        }catch(SeleniumException e){
            value = null
        }

        return value
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

    String getCookie(){
        return dispatcher.getCookie()
    }

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
        dispatcher.waitForFrameToLoa(frameAddress, timeout)
    }
}