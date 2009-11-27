package org.tellurium.test.helper

import junit.framework.AssertionFailedError

import org.tellurium.i18n.InternationalizationManager;


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

    private boolean passed

    private AssertionFailedError error

    protected InternationalizationManager i18nManager = new InternationalizationManager()


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
        sb.append(fieldStart).append(i18nManager.translate("AssertionResult.Successful")).append(avpSeparator).append(passed)
        if (error != null){
            sb.append(fieldSeparator)
            String errorMsg = error.getMessage()
            if(errorMsg == null)
                errorMsg = i18nManager.translate("AssertionResult.ResultComparisonError")
            sb.append(fieldStart).append(i18nManager.translate("AssertionResult.AssertionError")).append(avpSeparator).append("\"" + errorMsg + "\"")
        }

        return sb.toString()
    }

}