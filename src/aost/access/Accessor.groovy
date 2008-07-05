package aost.access

import aost.util.Helper
import aost.dispatch.Dispatcher

class Accessor{

    protected static final int ACCESS_WAIT_TIME = 50

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
/*
	def static boolean waitForCondition(String script, String timeoutInMilliSecond){

		boolean result = true

		try {
			dispatcher.waitForCondition(script, timeoutInMilliSecond)
		} catch (Exception e) {
			result = false
		}

		return result
	}
*/

	def String getText(String locator){
		String text = null

        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }
    	if (dispatcher.isElementPresent(locator)){
    		text = dispatcher.getText(locator)
    	}

    	return text;
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

    def String[] getSelectOption(String locator){
        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getSelectOptions(locator)
        }

        return null;
    }

    String getAttribute(String locator){

/*        if(!dispatcher.isElementPresent(locator)) {
            waitForElementPresent(locator, ACCESS_WAIT_TIME)
        }

        if(dispatcher.isElementPresent(locator)){
            return dispatcher.getAttribute(locator)
        }

        return null;*/
        return dispatcher.getAttribute(locator)
    }
}