package example.test.ddt

import org.tellurium.test.ddt.TelluriumDataDrivenTest

/**
 * Data Driven testing example to demonstrate how to write multiple Data Driven Modules and then include them in
 * the test class
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
    class GoogleBookListCodeHostTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {

        includeModule  example.test.ddt.GoogleBookListModule.class
        includeModule  example.test.ddt.GoogleCodeHostingModule.class

        //load file
        loadData "src/test/resources/example/test/ddt/GoogleBookListCodeHostInput.txt"
        
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }

}