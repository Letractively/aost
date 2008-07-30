package org.tellurium.test.helper

import java.lang.reflect.UndeclaredThrowableException
import org.tellurium.util.Helper

/**
 * Test result
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class TestResult {

    private static final String STEP_ID = "stepId"
    //identifier for a step
    private int stepId

    private static final String TEST_NAME = "testName"
    private String testName

    private static final String INPUT = "input"
    private Map input

    private static final String EXPECTED = "expected"
    private def expected

    private static final String ACTUAL = "actual"
    private def actual

    private static final String PASSED = "passed"
    private boolean passed

    private static final String STATUS = "status"
    private StepStatus status

    private static final String EXCEPTION = "exception"
    private Exception exception

    private static final String START = "start"
    private long start

    private static final String END = "end"
    private long end

    private static final String RUN_TIME = "runtime"
    
    public String toString(){
        final int typicalLength = 128
        final String avpSeparator = ":"
        final String fieldSeparator = "\n"
        final String fieldStart = "\t"

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(fieldStart).append(STEP_ID).append(avpSeparator).append(stepId).append(fieldSeparator)
        sb.append(fieldStart).append(TEST_NAME).append(avpSeparator).append(testName).append(fieldSeparator)
        sb.append(fieldStart).append(INPUT).append(avpSeparator).append(convertInput()).append(fieldSeparator)
        sb.append(fieldStart).append(EXPECTED).append(avpSeparator).append(expected).append(fieldSeparator)
        sb.append(fieldStart).append(ACTUAL).append(avpSeparator).append(actual).append(fieldSeparator)
        sb.append(fieldStart).append(PASSED).append(avpSeparator).append(passed).append(fieldSeparator)
        sb.append(fieldStart).append(STATUS).append(avpSeparator).append(status?.toString()).append(fieldSeparator)
        sb.append(fieldStart).append(RUN_TIME).append(avpSeparator).append((end-start)/1E9).append(" secs").append(fieldSeparator)
        if(exception != null)
            sb.append(fieldStart).append(EXCEPTION).append(avpSeparator).append(Helper.logException(exception)).append(fieldSeparator)

        return sb.toString()
    }

    private String convertInput(){
        String str

        if(input!= null && (!input.isEmpty())){
            List<String> list = new ArrayList<String>()
            input.each { key, value ->
                list.add("${key}: ${value}")
            }
            str = list.join(", ")
        }else{
            str = "empty"
        }

        return str
    }
}