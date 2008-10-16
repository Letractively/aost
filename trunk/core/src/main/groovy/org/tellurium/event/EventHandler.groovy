package org.tellurium.event

import org.tellurium.config.Configurable
import org.tellurium.dispatch.Dispatcher
import org.tellurium.util.Helper

class EventHandler implements Configurable{

    public static final String RETURN_KEY= "BSBS13"
	public static final int ACTION_WAIT_TIME = 50

    //default is selenium dispatcher
    def dispatcher  = new Dispatcher()
    private EventSorter alg = new EventSorter()

    private boolean checkElement = true
    private boolean extraEvent = true

    public void mustCheckElement(){
        this.checkElement = true
    }

    public void notCheckElement(){
        this.checkElement = false
    }

    public void useExtraEvent(){
        this.extraEvent = true
    }

    public void noExtraEvent(){
        this.extraEvent = false
    }

    protected void processingEvent(String locator, Event event){
        switch (event){
            case Event.BLUR:
                dispatcher.fireEvent(locator, "blur")
                break
            case Event.FOCUS:
                dispatcher.fireEvent(locator, "focus")
                break
            case Event.MOUSEOUT:
                dispatcher.mouseOut(locator)
                break
            case Event.MOUSEOVER:
                dispatcher.mouseOver(locator)
                break
            default:
                println "Warning: Unknown Event ${event.toString()}"
        }        
    }

    protected void processEvents(String locator, String[] events, String[] defaultEvents, Closure action){
        checkElement(locator)
        if(dispatcher.isElementPresent(locator)){
            Event[] evns = alg.sort(events, defaultEvents)
            evns.each { Event event ->
                if(event == Event.ACTION)
                  action()
                else
                  processingEvent(locator, event)
            }

        }
    }

    protected void checkElement(String locator){
		if(checkElement && (!dispatcher.isElementPresent(locator))){
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
		}
    }

    def mouseOver(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = ["focus"]

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseOver(locator)
        }
	}

    def mouseOut(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = ["focus"]

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseOut(locator)
        }
	}

	def click(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(locator, events, defaultEvents){
           dispatcher.click(locator)
        }
	}

	def doubleClick(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(locator, events, defaultEvents){
           dispatcher.doubleClick(locator)
        }
	}

	def clickAt(String locator, String coordination, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(locator, events, defaultEvents){
           dispatcher.clickAt(locator, coordination)
        }
	}

	def check(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(locator, events, defaultEvents){
           dispatcher.check(locator)
        }
	}

	def uncheck(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver"]

        processEvents(locator, events, defaultEvents){
           dispatcher.uncheck(locator)
        }
    }

	def type(String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(locator, events, defaultEvents){
           dispatcher.type(locator, input)
        }
    }

    def keyType(String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(locator, events, defaultEvents){
           simulateKeyType(locator, input)
        }
	}

	def typeAndReturn(String locator, String input, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(locator, events, defaultEvents){
            dispatcher.type(locator, input)
            dispatcher.keyUp(locator,  "\\13")
        }
	}

    def clearText(String locator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(locator, events, defaultEvents){
           dispatcher.type(locator, "")
        }
	}

    def simulateKeyType(String locator, String input){

        if(input == null || input.length() < 1){
    		dispatcher.type(locator, "")
        }else if(input.contains('.') || input.contains('(')){
            dispatcher.type(locator, input)
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

	def select(String locator, String target, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
           defaultEvents = ["focus", "mouseOver", "mouseOut", "blur"]

        processEvents(locator, events, defaultEvents){
	        dispatcher.click(locator)
			checkAndWaitForElementPresent(locator, ACTION_WAIT_TIME)
			dispatcher.select(locator, target)
        }
    }

    def addSelection(String locator, String optionLocator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.addSelection(locator, optionLocator)
		}
    }

    def removeSelection(String locator,String optionLocator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.removeSelection(locator, optionLocator)
		}
    }

    def removeAllSelections(String locator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.removeAllSelections(locator)
		}
    }

    def submit(String locator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.submit(locator)
		}
    }

    void openWindow(String url, String windowID){
        dispatcher.openWindow(url, windowID)
    }

    void selectWindow(String windowID){
        dispatcher.selectWindow(windowID)   
    }

    void selectFrame(String locator){
        dispatcher.selectFrame(locator)
    }

    String waitForText(String locator, int timeout){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){

//			dispatch.isTextPresent((new Integer(timeout)).toString())
            dispatcher.isTextPresent(timeout)

            return dispatcher.getText(locator)
		}

		return null
	}

    def private boolean checkAndWaitForElementPresent(String locator, int timeout){

		boolean result = false

        for (int second = 0; second < timeout; second+=500) {
            try {
            	if (dispatcher.isElementPresent(locator)){
            		result = true
            		break
            		}
            }catch (Exception e){

            }

            Helper.pause(500)
        }

        return result
	}

    void chooseCancelOnNextConfirmation(){
        dispatcher.chooseCancelOnNextConfirmation()
    }

    void chooseOkOnNextConfirmation(){
        dispatcher.chooseOkOnNextConfirmation()
    }

    void answerOnNextPrompt(String answer){
        dispatcher.answerOnNextPrompt(answer)
    }

    void goBack(){
        dispatcher.goBack()
    }

    void refresh(){
        dispatcher.refresh()
    }

}