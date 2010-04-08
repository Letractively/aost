package org.telluriumsource.entity.config.test

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Exception {
  //whether Tellurium captures the screenshot when exception occurs.
  //Note that the exception is the one thrown by Selenium Server
  //we do not care the test logic errors here
  String captureScreenshot = false;
  //we may have a series of screenshots, specify the file name pattern here
  //Here the ? will be replaced by the timestamp and you might also want to put
  //file path in the file name pattern
  String filenamePattern = "Screenshot?.png";  
}
