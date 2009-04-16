package org.tellurium.event

import org.tellurium.config.Configurable
import org.tellurium.dispatch.Dispatcher
import org.tellurium.event.Event
import org.tellurium.event.EventSorter
import org.tellurium.exception.ElementNotPresentException
import org.tellurium.util.Helper
import java.awt.event.KeyEvent

class EventHandler implements Configurable{

    public static final String RETURN_KEY= "BSBS13"
	public static final int ACTION_WAIT_TIME = 50
    protected static final String ELEMENT_NOT_PRESENT_ERROR_MESSAGE = "Element is not present"

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
            case Event.MOUSEDOWN:
                dispatcher.mouseDown(locator)
                break
            case Event.MOUSEUP:
                dispatcher.mouseUp(locator)
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

        }else{
          throw new ElementNotPresentException("${ELEMENT_NOT_PRESENT_ERROR_MESSAGE} ${locator}")
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

   def dragAndDrop(String locator, String movementsString, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = []

        processEvents(locator, events, defaultEvents){
            dispatcher.dragAndDrop(locator, movementsString)
        }
	}

   def dragAndDropToObject(String srcLocator, String targetLocator, String[] events) {
        String[] defaultEvents = null
        if(extraEvent)
            defaultEvents = []

        processEvents(srcLocator, events, defaultEvents){
            dispatcher.dragAndDropToObject(srcLocator, targetLocator)
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
           if(includeKeyEvents(events)){
               processKeyEvent(locator, input, events)
           }else{
               dispatcher.type(locator, input)
           }
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
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				dispatcher.keyUp(locator,  "\\13")
                }else if(".".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
//                    dispatcher.keyDownNative(keycode)
                    dispatcher.keyPressNative(keycode)
//                    dispatcher.keyUpNative(keycode)
                }else if("(".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
//                    dispatcher.keyDownNative(keycode)
                    dispatcher.keyPressNative(keycode)
//                    dispatcher.keyUpNative(keycode)
                }else if("y".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_Y)
//                    dispatcher.keyDownNative(keycode)
                    dispatcher.keyPressNative(keycode)
//                    dispatcher.keyUpNative(keycode)
                }else{
    				dispatcher.keyDown(locator, key)
    				dispatcher.keyPress(locator, key)
    				dispatcher.keyUp(locator, key)
    			}
    			Helper.pause(15)
    		}
    	}
    }

    def processKeyEvent(String locator, String input, String[] events){
        boolean hasKeyDown = includeKeyDown(events)
        boolean hasKeyPress = includeKeyPress(events)
        boolean hasKeyUp = includeKeyUp(events)

        if(input == null || input.length() < 1){
    		dispatcher.type(locator, "")
        }else{
    		char[] chars = input.toCharArray()

            for(char achar: chars){
    			String key = Character.toString(achar)
    			if("\n".equals(key)){
    				dispatcher.keyUp(locator,  "\\13")
                }else if(".".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_PERIOD)
                    if(hasKeyDown)
                      dispatcher.keyDownNative(keycode)

                    dispatcher.keyPressNative(keycode)

                    if(hasKeyUp)
                      dispatcher.keyUpNative(keycode)
                }else if("(".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_LEFT_PARENTHESIS)
                    if(hasKeyDown)
                      dispatcher.keyDownNative(keycode)

                    dispatcher.keyPressNative(keycode)

                    if(hasKeyUp)
                      dispatcher.keyUpNative(keycode)
                }else if("y".equals(key)){
                    String keycode = Integer.toString(KeyEvent.VK_Y)
                    if(hasKeyDown)
                      dispatcher.keyDownNative(keycode)

                    dispatcher.keyPressNative(keycode)

                    if(hasKeyUp)
                      dispatcher.keyUpNative(keycode)
                }else{
                    if(hasKeyDown)
                        dispatcher.keyDown(locator, key)

                    dispatcher.keyPress(locator, key)

                    if(hasKeyUp)
                        dispatcher.keyUp(locator, key)
    			}
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
		}else{
          throw new ElementNotPresentException("${ELEMENT_NOT_PRESENT_ERROR_MESSAGE} ${locator}")
        }
    }

    def removeSelection(String locator,String optionLocator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.removeSelection(locator, optionLocator)
		}else{
          throw new ElementNotPresentException("${ELEMENT_NOT_PRESENT_ERROR_MESSAGE} ${locator}")
        }
    }

    def removeAllSelections(String locator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.removeAllSelections(locator)
		}else{
          throw new ElementNotPresentException("${ELEMENT_NOT_PRESENT_ERROR_MESSAGE} ${locator}")
        }
    }

    def submit(String locator){
        checkElement(locator)

		if(dispatcher.isElementPresent(locator)){
            if(extraEvent){
                dispatcher.fireEvent(locator, "focus")
            }
            dispatcher.submit(locator)
		}else{
          throw new ElementNotPresentException("${ELEMENT_NOT_PRESENT_ERROR_MESSAGE} ${locator}")
        }
    }

    void openWindow(String url, String windowID){
        dispatcher.openWindow(url, windowID)
    }

    void selectWindow(String windowID){
        dispatcher.selectWindow(windowID)   
    }

    void windowFocus(){
        dispatcher.windowFocus()
    }

    def closeWindow(String windowID){
        dispatcher.selectWindow(windowID)
        dispatcher.close()
    }

    void windowMaximize(){
        dispatcher.windowMaximize()
    }

    void selectFrame(String locator){
        dispatcher.selectFrame(locator)
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

    protected boolean includeKeyEvents(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYDOWN.toString().equalsIgnoreCase(event) || Event.KEYPRESS.toString().equalsIgnoreCase(event)
                    || Event.KEYUP.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyDown(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYDOWN.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyPress(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYPRESS.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }

    protected boolean includeKeyUp(String[] events){
        boolean result = false

        if(events != null){
            for(String event : events){
                if(Event.KEYUP.toString().equalsIgnoreCase(event)){
                    result = true
                    break
                }
            }
        }

        return result
    }


    def mouseDown(String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseDown(locator)
        }
	}

    def mouseDownRight(String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseDownRight(locator)
        }
	}

    def mouseDownRightAt(String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseDownRightAt(locator, coordinate)
        }
	}

    def mouseUp(String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseUp(locator)
        }
	}

    def mouseUpRight(String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseUpRight(locator)
        }
	}

    def mouseUpRightAt(String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseUpRightAt(locator, coordinate)
        }
	}

    def mouseMove(String locator, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseMove(locator)
        }
	}

    def mouseMoveAt(String locator, String coordinate, String[] events) {
        String[] defaultEvents = null

        processEvents(locator, events, defaultEvents){
            dispatcher.mouseMoveAt(locator, coordinate)
        }
	}

}