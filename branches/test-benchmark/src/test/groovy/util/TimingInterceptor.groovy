package util

/*
 *   Util class to trace the execution time for each method
 */
public class TimingInterceptor extends TracingInterceptor{

    private long accumulatedTime = 0

    private long beforeTime

    private List<String> whiteList = []

    public void setWhiteList(List<String> list){
      this.whiteList = list
    }

    public long getAccumulatedTime(){
      return this.accumulatedTime
    }

    public void resetAccumulatedTime(){
      this.accumulatedTime = 0
    }
  
    protected boolean methodInWhiteList(String methodName){
      if(whiteList.indexOf(methodName) != -1)
        return true
      
      return false
    }

    def beforeInvoke(object, String methodName, Object[] arguments) {
        super.beforeInvoke(object, methodName, arguments)
        beforeTime = System.currentTimeMillis()
    }
  
    public Object afterInvoke(Object object, String methodName, Object[] arguments, Object result) {
        super.afterInvoke(object, methodName, arguments, result)
        def duration = System.currentTimeMillis() - beforeTime
        if(methodInWhiteList(methodName)){
          accumulatedTime += duration
        }
        writer.write("Duration for method ${methodName}: $duration ms\n")
        writer.flush()
        return result
    }

}