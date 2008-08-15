package org.tellurium.test.helper

import junit.framework.AssertionFailedError

/**
 * Hold the result for the assertion of
 *
 *  compareResult(expected, actual)
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
class AssertionResult {

    private static final String EXPECTED = "Expected"
    private def expected

    private static final String ACTUAL = "Actual"
    private def actual

    private static final String PASSED = "Successful"
    private boolean passed

    private static final String ASSERTION_ERROR = "Error"
    private AssertionFailedError error

    public boolean isPassed(){
        return passed
    }

    public String toString() {
        final int typicalLength = 128
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(EXPECTED).append(avpSeparator).append("\"" + expected + "\"").append(fieldSeparator)
        sb.append(fieldStart).append(ACTUAL).append(avpSeparator).append("\"" + actual + "\"").append(fieldSeparator)
        sb.append(fieldStart).append(PASSED).append(avpSeparator).append(passed)
        if (error != null){
            sb.append(fieldSeparator)
            String errorMsg = error.getMessage()
            if(errorMsg == null)
                errorMsg = "Result Comparison Error"
            sb.append(fieldStart).append(ASSERTION_ERROR).append(avpSeparator).append("\"" + errorMsg + "\"")
        }

        return sb.toString()
    }

}