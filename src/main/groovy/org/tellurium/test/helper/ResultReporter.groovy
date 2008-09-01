package org.tellurium.test.helper

/**
 * store, log, or other ways to process the test result
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 26, 2008
 *
 */
interface ResultReporter {
    //change the return value to be string so that we can use decorate pattern to process the result further
    String report(List<TestResult> results)
    
}