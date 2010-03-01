package org.tellurium.test.helper

import org.tellurium.util.Helper

/**
 * convert the test results in XML format
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 3, 2008
 * 
 */
class XMLResultReporter implements ResultReporter{

    public String report(List<TestResult> results) {
        int total = 0
        int succeeded = 0
        int failed = 0
        if (results != null && (!results.isEmpty())) {
            total = results.size()
            results.each {TestResult val ->
                if (val.isPassed()) {
                    succeeded++
                } else {
                    failed++
                }
            }
        }

        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)
        xml.TestResults {
            Total("${total}")
            Succeeded("${succeeded}")
            Failed("${failed}")
            results.each {result ->
                Test(name: result.testName) {
                    Step(result.stepId)
                    Passed(result.isPassed())
                    Input {
                        result.input.each {key, value ->
                            "${key}"(value)
                        }
                    }
                    result.assertionResults.each {AssertionResult ar ->
                        if (ar.value instanceof ComparisonAssertionValue) {
                            if (ar.error != null)
                                Assertion(Expected: ar.value.expected, Actual: ar.value.actual, Passed: ar.passed, Error: ar.error?.getMessage())
                            else
                                Assertion(Expected: ar.value.expected, Actual: ar.value.actual, Passed: ar.passed)
                        } else if (ar.value instanceof EvaulationAssertionValue) {
                            if (ar.error != null)
                                Assertion(Value: ar.value.value, Passed: ar.passed, Error: ar.error?.getMessage())
                            else
                                Assertion(Value: ar.value.value, Passed: ar.passed)
                        } else {
                            //unknown assertion type
                        }
                    }
                    Status(result.status.toString())
                    Runtime((result.end - result.start) / 1E9)
                    if (result.messages != null && (!result.messages.isEmpty())) {
                        result.messages.each {message ->
                            Message("${message}")
                        }
                    }
                    if (result.exception != null)
                        Exception(Helper.logException(result.exception))
                }
            }
        }

        return writer.toString()
    }
}