package aost.locator

class LocatorProcessor{
    public static final String CANNOT_HANDLE_LOCATOR= "Cannot handle locator"

    def String locate(locator){
        if(locator == null)
            return ""

        if(locator instanceof BaseLocator)
            return DefaultLocateStrategy.locate(locator)

        if(locator instanceof CompositeLocator)
            return AutoLocateStrategy.locate(locator)
        
        throw RuntimeException(CANNOT_HANDLE_LOCATOR + " " + locator.getClass())
    }
}