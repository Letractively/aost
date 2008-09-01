package org.tellurium.test.helper
/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 4, 2008
 *
 */
class StreamXMLResultReporter implements ResultReporter{

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

        def xml = new groovy.xml.StreamingMarkupBuilder().bind {
            mkp.xmlDeclaration()
            mkp.declareNamespace(ns: "http://code.google.com/p/aost/")
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
                        result.assertionResults.each {ar ->
                            if (ar.error != null)
                                Assertion(Expected: ar.expected, Actual: ar.actual, Passed: ar.passed, Error: ar.error?.getMessage())
                            else
                                Assertion(Expected: ar.expected, Actual: ar.actual, Passed: ar.passed)
                        }
                        Status(result.status.toString())
                        Runtime((result.end - result.start) / 1E9)
                        if (result.exception != null)
                            Exception(Helper.logException(result.exception))
                    }
                }
            }
        }

        return xml.toString()        
    }

}