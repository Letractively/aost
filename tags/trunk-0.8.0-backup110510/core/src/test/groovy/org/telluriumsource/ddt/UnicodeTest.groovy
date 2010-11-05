package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenTest

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2010
 * 
 */
class UnicodeTest extends TelluriumDataDrivenTest{
  void testDataDriven() {
    includeModule org.telluriumsource.ddt.UnicodeModule.class
    loadData "src/test/resources/data/unicode.txt"
    stepToEnd()
    //close file
    closeData()

  }
}
