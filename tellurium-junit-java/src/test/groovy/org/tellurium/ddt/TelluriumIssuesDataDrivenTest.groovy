package org.tellurium.ddt

import org.tellurium.test.ddt.TelluriumDataDrivenTest

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

        includeModule org.tellurium.module.TelluriumIssuesModule.class

        //load file
        loadData "src/test/resources/org/tellurium/data/TelluriumIssuesInput.txt"

        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }

}