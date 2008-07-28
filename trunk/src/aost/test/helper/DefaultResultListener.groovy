package aost.test.helper
/**
 * Default implementation of Test Listener
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class DefaultResultListener implements ResultListener {
    
    private List<TestResult> results = new ArrayList<TestResult>()

    public void listenForResult(TestResult result) {
        results.add(result)
    }

}