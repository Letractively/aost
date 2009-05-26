package org.tellurium.access

import org.tellurium.config.Configurable
import org.tellurium.util.Helper
import org.tellurium.dsl.WorkflowContext
import org.tellurium.bundle.CommandBundleProcessor
import org.tellurium.bundle.ReturnType

class Accessor implements Configurable{

    protected static final int ACCESS_WAIT_TIME = 50

    private static final String DISABLED_ATTRIBUTE = "@disabled"

    protected static final String ELEMENT_NOT_PRESENT_ERROR_MESSAGE = "Element is not present"

    private CommandBundleProcessor cbp  = CommandBundleProcessor.instance

    private boolean checkElement = true

    public void mustCheckElement(){
        this.checkElement = true
    }

    public void notCheckElement(){
        this.checkElement = false
    }

    protected void checkElement(WorkflowContext context, String locator){
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)

		if(checkElement && (!cbp.isElementPresent(context, locator))){
			waitForElementPresent(context, locator, ACCESS_WAIT_TIME)
		}
    }

    def boolean isElementPresent(WorkflowContext context, String locator){
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)

		return cbp.isElementPresent(context, locator)
	}

    def boolean isVisible(WorkflowContext context, String locator){
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)
    	return cbp.isVisible(context, locator)
    }

	def boolean isChecked(WorkflowContext context, String locator) {
        checkElement(context, locator)
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)

		return cbp.isChecked(context, locator)
	}

    def boolean isDisabled(WorkflowContext context, String locator){

        checkElement(context, locator)
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        locator +=DISABLED_ATTRIBUTE
        String attr = getAttribute(context, locator)
        return ( attr!= null && (attr == "true"|| attr == "disabled"));
    }

   def boolean waitForElementPresent(WorkflowContext context, String locator, int timeout){

		//boolean result = false
        ReturnType type = context.getCallReturnType()
        context.setCallReturnType(ReturnType.BOOLEAN)
        for (int second = 0; second < timeout; second+=500) {
            try {
            	if (cbp.isElementPresent(context, locator)){
            		//result = true
            		return true
            		}
            }catch (Exception e){

            }

            Helper.pause(500)
        }

        context.setCallReturnType(type)
        //return result;
        return false
    }

    def boolean waitForElementPresent(WorkflowContext context, String locator, int timeout, int step){

		//boolean result = false
        ReturnType type = context.getCallReturnType()
        context.setCallReturnType(ReturnType.BOOLEAN)

        for (int second = 0; second < timeout; second += step) {
            try {
            	if (cbp.isElementPresent(context, locator)){
            		//result = true
            		return true
                }
            }catch (Exception e){

            }

            Helper.pause(step)
        }

        context.setCallReturnType(type)
        //return result;
        return false
    }

	boolean waitForCondition(WorkflowContext context, String script, String timeoutInMilliSecond){

		boolean result = true
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)
      
		try {
			cbp.waitForCondition(context, script, timeoutInMilliSecond)
		} catch (Exception e) {
			result = false
		}

		return result
	}

	def String getText(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

    	return cbp.getText(context, locator)
	}

	def String getValue(WorkflowContext context, String locator){

        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getValue(context, locator)
	}

    def String[] getSelectOptions(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getSelectOptions(context, locator)
    }

    String[] getSelectedLabels(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getSelectedLabels(context, locator)
    }

    String getSelectedLabel(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getSelectedLabel(context, locator)
    }

    String[] getSelectedValues(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getSelectedValues(context, locator)
    }

    String getSelectedValue(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getSelectedValue(context, locator)
    }

    String[] getSelectedIndexes(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getSelectedIndexes(context, locator)
    }

    String getSelectedIndex(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getSelectedIndex(context, locator)
    }

    String[] getSelectedIds(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getSelectedIds(context, locator)
    }

    String getSelectedId(WorkflowContext context, String locator){
        checkElement(context, locator)
      
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getSelectedId(context, locator)
    }

    boolean isSomethingSelected(WorkflowContext context, String locator){
        checkElement(context, locator)

        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)
      
        return cbp.isSomethingSelected(context, locator)
    }

    String getAttribute(WorkflowContext context, String locator){
      context.setCallLocatorSpecific(true)
      context.setCallReturnType(ReturnType.STRING)

      return cbp.getAttribute(context, locator)
    }

    void waitForPopUp(WorkflowContext context, String windowID, String timeout){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.VOID)
      
        cbp.waitForPopUp(context, windowID, timeout)
    }

    String getBodyText(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)
      
        return cbp.getBodyText(context)
    }

    boolean isTextPresent(WorkflowContext context, String pattern){
       context.setCallLocatorSpecific(false)
       context.setCallReturnType(ReturnType.BOOLEAN)

       return cbp.isTextPresent(context, pattern)
    }

    boolean isEditable(WorkflowContext context, String locator){
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.isEditable(context, locator)
    }

    String getHtmlSource(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getHtmlSource(context)
    }

    String getExpression(WorkflowContext context, String expression){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)
      
        return cbp.getExpression(context, expression)
    }

    Number getXpathCount(WorkflowContext context, String xpath){
        context.setCallLocatorSpecific(true)
        context.setCallReturnType(ReturnType.NUMBER)

        return cbp.getXpathCount(context, xpath)
    }

    String getCookie(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)
      
        return cbp.getCookie(context)
    }

    void runScript(WorkflowContext context, String script){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.VOID)
      
        cbp.runScript(context, script)
    }

    void captureScreenshot(WorkflowContext context, String filename){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.VOID)

        cbp.captureScreenshot(context, filename)
    }

    boolean isAlertPresent(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.isAlertPresent(context)
    }

    boolean isPromptPresent(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.isPromptPresent(context)
    }

    boolean isConfirmationPresent(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.isConfirmationPresent(context)
    }

    String getAlert(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getAlert(context)
    }

    String getConfirmation(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getConfirmation(context)
    }

    String getPrompt(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getPrompt(context)
    }

    String getLocation(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getLocation(context)
    }

    String getTitle(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getTitle(context)
    }

    String[] getAllButtons(WorkflowContext context){
       context.setCallLocatorSpecific(false)
       context.setCallReturnType(ReturnType.ARRAY)
      
       return cbp.getAllButtons(context)
    }

    String[] getAllLinks(WorkflowContext context){
       context.setCallLocatorSpecific(false)
       context.setCallReturnType(ReturnType.ARRAY)

       return cbp.getAllLinks(context)
    }

    String[] getAllFields(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getAllFields(context)
    }

    String[] getAllWindowIds(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getAllWindowIds(context)
    }

    String[] getAllWindowNames(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getAllWindowNames(context)
    }

    String[] getAllWindowTitles(WorkflowContext context){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.ARRAY)

        return cbp.getAllWindowTitles(context)
    }

    void waitForPageToLoad(WorkflowContext context, String timeout){
          context.setCallLocatorSpecific(false)
          context.setCallReturnType(ReturnType.VOID)
      
          cbp.waitForPageToLoad(context, timeout)
    }

    void waitForFrameToLoad(WorkflowContext context, String frameAddress, String timeout){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.VOID)
      
        cbp.waitForFrameToLoad(context, frameAddress, timeout)
    }

    String getEval(WorkflowContext context, String script){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.STRING)

        return cbp.getEval(context, script)
    }

    boolean getWhetherThisFrameMatchFrameExpression(WorkflowContext context, String currentFrameString, String target){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.getWhetherThisFrameMatchFrameExpression(context, currentFrameString, target)
    }

    boolean getWhetherThisWindowMatchWindowExpression(WorkflowContext context, String currentWindowString, String target){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.BOOLEAN)

        return cbp.getWhetherThisWindowMatchWindowExpression(context, currentWindowString, target)
    }

    void useXpathLibrary(WorkflowContext context, String libraryName){
        context.setCallLocatorSpecific(false)
        context.setCallReturnType(ReturnType.VOID)

        cbp.useXpathLibrary(context, libraryName)
    }

    String waitForText(WorkflowContext context, String locator, int timeout){

       waitForElementPresent(context, locator, timeout)

       context.setCallLocatorSpecific(false)
       context.setCallReturnType(ReturnType.STRING)

       return cbp.getText(context, locator)
	}
}