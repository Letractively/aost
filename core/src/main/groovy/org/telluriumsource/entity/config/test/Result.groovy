package org.telluriumsource.entity.config.test

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Result {
  //specify what result reporter used for the test result
  //valid options include "SimpleResultReporter", "XMLResultReporter", and "StreamXMLResultReporter"
  String reporter = "XMLResultReporter";
  //the output of the result
  //valid options include "Console", "File" at this point
  //if the option is "File", you need to specify the file name, other wise it will use the default
  //file name "TestResults.output"
  String output = "Console";
  //test result output file name
  String filename = "TestResult.output";
}
