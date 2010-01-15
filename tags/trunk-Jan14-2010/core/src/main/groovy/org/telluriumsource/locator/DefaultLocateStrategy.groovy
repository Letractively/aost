package org.telluriumsource.locator

class DefaultLocateStrategy{

    def static boolean canHandle(locator){
       if(locator instanceof BaseLocator)
        return true
       else
        return false
    }

    def static String locate(BaseLocator locator){

        locator.loc
    }
}