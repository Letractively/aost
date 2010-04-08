package org.telluriumsource.entity.config.datadriven

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class DataProvider {
  //specify which data reader you like the data provider to use
  //the valid options include "PipeFileReader", "CSVFileReader" , "ExcelFileReader" at this point
  String reader = "PipeFileReader";
}
