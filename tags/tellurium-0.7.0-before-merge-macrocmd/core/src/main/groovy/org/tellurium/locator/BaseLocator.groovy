package org.tellurium.locator

class BaseLocator{
    String loc

    public String getTag(){
        //TODO: parse the tag from the base locator if it is XPATH, how about Selector?
        return ""
    }

    public generateHtml(boolean closeTag){
      //Do not support generate Html for base locator
      return ""
    }
  
    public String generateCloseTag(){
      return ""
    }
}