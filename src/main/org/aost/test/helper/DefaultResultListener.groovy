package org.aost.test.helper
/**
 * Default implementation of Test Listener
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class DefaultResultListener implements ResultListener {
    
    private Map<Integer, TestResult> results = new HashMap<Integer, TestResult>()

    public void listenForResult(TestResult result) {
        TestResult tr = results.get(result.getProperty("stepId"))
        if(tr != null){
            tr.setProperty("expected", result.getProperty("expected"))
            tr.setProperty("actual", result.getProperty("actual"))
            tr.setProperty("passed", result.getProperty("passed"))
        }

        results.put(result.getProperty("stepId"), tr)
    }

    public void listenForInput(TestResult result) {
        //TODO: may need to refactor the follow code to address more complicated situations
        
        results.put(result.getProperty("stepId"), result)
    }

}