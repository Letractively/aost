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

    void report(List<TestResult> results)
    
}