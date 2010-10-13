package org.telluriumsource.test.report

import junit.framework.AssertionFailedError
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.framework.SessionManager
import org.telluriumsource.annotation.Inject;



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

    @Inject(name="i18nBundle", lazy=true)
    private IResourceBundle i18nBundle
  
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
        sb.append(fieldStart).append(i18nBundle.getMessage("AssertionResult.Successful")).append(avpSeparator).append(passed)
        if (error != null){
            sb.append(fieldSeparator)
            String errorMsg = error.getMessage()
            if(errorMsg == null)
                errorMsg = i18nBundle.getMessage("AssertionResult.ResultComparisonError")
            sb.append(fieldStart).append(i18nBundle.getMessage("AssertionResult.AssertionError")).append(avpSeparator).append("\"" + errorMsg + "\"")
        }

        return sb.toString()
    }

}