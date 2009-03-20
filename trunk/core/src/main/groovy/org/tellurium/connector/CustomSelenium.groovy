package org.tellurium.connector

import com.thoughtworks.selenium.DefaultSelenium

/**
 * Customize Selenium RC so that we can add custom methods to Selenium RC
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 21, 2008
 * 
 */
class CustomSelenium extends DefaultSelenium {
    
    CustomSelenium(String host, int port, String browser, String url) {
        super(host, port, browser, url)
    }

    /*Please add custom methods here for Selenium RC after you add user extension to Selenium Core

   For instance,

       public void typeRepeated(String locator, String text) {

         commandProcessor.doCommand("typeRepeated", new String[]{locator, text});

       }
    */
  
	def String getSelectorProperties(String jqSelector, String props){
		String[] arr = [jqSelector, props];
		String st = commandProcessor.doCommand("getSelectorProperties", arr);
		return st;
	}

	def String getSelectorText(String jqSelector){
		String[] arr = [jqSelector];
		String st = commandProcessor.doCommand("getSelectorText", arr);
		return st;
	}

	def String getSelectorFunctionCall(String jqSelector, String args){
		String[] arr = [jqSelector, args];
		String st = commandProcessor.doCommand("getSelectorFunctionCall", arr);
		return st;
	}
}