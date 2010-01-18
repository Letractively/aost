package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.TelluriumDataDrivenTest

/**
 * Date driven testing for Tellurium issues page
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 19, 2008
 * 
 */
class TelluriumIssuesDataDrivenTest extends TelluriumDataDrivenTest{


    public void testDataDriven() {

        includeModule org.telluriumsource.module.TelluriumIssuesModule.class

        //load file
        loadData "src/test/resources/org/tellurium/data/TelluriumIssuesInput.txt"

        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }

}