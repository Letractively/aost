package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenModule

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2010
 * 
 */
class UnicodeModule extends TelluriumDataDrivenModule{
  void defineModule() {
    typeHandler "unicode", "org.telluriumsource.ddt.UnicodeTypeHandler"

    fs.FieldSet(name: "record", description: "Data format for testing Unicode") {
        Test(value: "testUnicode")
        Field(name: "title", description: "test title")
        Field(name: "abstract", type: "unicode", description: "abstract")
        Field(name: "email", description: "email")
        Field(name: "indicator", type: "boolean", description: "indicator")
    }

    defineTest("testUnicode") {
      String title = bind("record.title")
      String abst = bind("record.abstract")
      String email = bind("record.email")
      boolean indicator = bind("record.indicator")
      compareResult(false, indicator)
      println "$title, $abst, $email, $indicator"
    }

  }
}
