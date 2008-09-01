package aost.event

import aost.util.Helper
import aost.dispatch.Dispatcher

class EventHandler{

    public static final String RETURN_KEY= "BSBS13"
	public static final int ACTION_WAIT_TIME = 50

    //default is selenium dispatcher
    def dispatcher  = new Dispatcher()
//    def Dispatcher dispatcher

	def click(String locator) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
			dispatcher.click(locator)
		}
	}

	def doubleClick(String locator) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
			dispatcher.doubleClick(locator)
		}
	}

	def clickAt(String locator, String coordination) {
		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
			dispatcher.clickAt(locator, coordination)
		}
	}

	def check(String locator) {
		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.check(locator)
		}
	}

	def uncheck(String locator) {
		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.uncheck(locator)
		}
	}

	def type(String locator, String input) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
//			dispatcher.setCursorPosition(locator, "0")
//			simulateKeyType(locator, input)
			dispatcher.type(locator, input)
			dispatcher.mouseOut(locator)
			dispatcher.fireEvent(locator, "blur")
		}
	}

    def keyType(String locator, String input) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
//			dispatcher.setCursorPosition(locator, "0")
			simulateKeyType(locator, input)
			dispatcher.mouseOut(locator)
			dispatcher.fireEvent(locator, "blur")
		}
	}

	def typeAndReturn(String locator, String input) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
//			dispatcher.setCursorPosition(locator, "0")
//			simulateKeyType(locator, input)
            dispatcher.type(locator, input)
            dispatcher.keyUp(locator,  "\\13")
			dispatcher.mouseOut(locator)
			dispatcher.fireEvent(locator, "blur")
		}
	}

    def clearText(String locator) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.mouseOver(locator)
//			dispatcher.setCursorPosition(locator, "0")
			dispatcher.type(locator, "")
			dispatcher.mouseOut(locator)
			dispatcher.fireEvent(locator, "blur")
		}
	}

    def simulateKeyType(String locator, String input){

        if(input == null || input.length() < 1){
    		dispatcher.type(locator, "")
    	}else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				dispatcher.keyUp(locator,  "\\13")
    			}else{
    				dispatcher.keyDown(locator, key)
    				dispatcher.keyPress(locator, key)
    				dispatcher.keyUp(locator, key)
    			}
    			Helper.pause(15)
    		}
    	}
    }

	def select(String locator, String target) {

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){
			dispatcher.fireEvent(locator, "focus")
			dispatcher.mouseOver(locator)
			dispatcher.click(locator)
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
			dispatcher.select(locator, target)
			dispatcher.mouseOut(locator)
			dispatcher.fireEvent(locator, "blur")
		}
	}

    def String waitForText(String locator, int timeout){

		if(!dispatcher.isElementPresent(locator)){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}

		if(dispatcher.isElementPresent(locator)){

//			dispatch.isTextPresent((new Integer(timeout)).toString())
            dispatcher.isTextPresent(timeout)

            return dispatcher.getText(locator)
		}

		return null
	}

    def private boolean checkAndWaitForElementPresent(String locator, int timeout){

		boolean result = false

        for (int second = 0; second < timeout; second+=1000) {
            try {
            	if (dispatcher.isElementPresent(locator)){
            		result = true
            		break
            		}
            }catch (Exception e){

            }

            Helper.pause(1000)
        }

        return result
	}

}