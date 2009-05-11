package org.tellurium.test.helper

import junit.framework.AssertionFailedError

/**
 * Hold the result for the assertions
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 1, 2008
 *
 */
class AssertionResult {

    private static final String VALUE = "value"
    private AssertionValue value

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
        sb.append(value.toString())
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