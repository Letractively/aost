package org.aost.test.helper
/**
 * Listen to the test result
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 26, 2008
 * 
 */
interface ResultListener {

    public void listenForInput(TestResult result)
    
    public void listenForResult(TestResult result)

    void report()
}