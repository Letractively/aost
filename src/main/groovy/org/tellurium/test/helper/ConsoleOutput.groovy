package org.tellurium.test.helper
/**
 * print the result to the console
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 4, 2008
 *
 */
class ConsoleOutput implements ResultOutput{

    public String output(String results) {

        //simply printout the results to the screen
        print results

        //still return the results so that other processor can process it
        return results
    }

}