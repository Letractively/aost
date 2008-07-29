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

    private List<ResultReporter> reporters = new ArrayList<ResultReporter>()

    public DefaultResultListener(){
        reporters.add(new SimpleResultReporter())
    }
    
    public void addReporter(ResultReporter reporter){
        reporters.add(reporter)
    }

    public void listenForResult(TestResult result) {
        TestResult tr = results.get(result.getProperty("stepId"))
        if(tr != null){
            tr.setProperty("expected", result.getProperty("expected"))
            tr.setProperty("actual", result.getProperty("actual"))
            tr.setProperty("passed", result.getProperty("passed"))
            if(result.getProperty("exception") != null){
                tr.setProperty("exception", result.getProperty("exception"))
            }

            results.put(result.getProperty("stepId"), tr)
        }else{
            results.put(result.getProperty("stepId"), result)
        }
    }

    public void listenForInput(TestResult result) {
        TestResult tr = results.get(result.getProperty("stepId"))
        if(tr != null){
            tr.setProperty("testName", result.getProperty("testName"))
            tr.setProperty("input", result.getProperty("input"))
            tr.setProperty("start", result.getProperty("start"))
            tr.setProperty("end", result.getProperty("end"))
            tr.setProperty("status", result.getProperty("status"))
            if(result.getProperty("exception") != null){
                tr.setProperty("exception", result.getProperty("exception"))
            }
            
            results.put(result.getProperty("stepId"), tr)
        }else{
            results.put(result.getProperty("stepId"), result)
        }
    }

    public void report() {
        if(!this.results.isEmpty()){
            List<TestResult> trl = this.results.values().sort { x, y ->
                x.stepId <=> y.stepId
            }

            if(!reporters.isEmpty()){
                for(ResultReporter reporter : reporters){
                    reporter.report(trl)
                }
            }
        }
    }

}