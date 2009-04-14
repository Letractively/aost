package util

public class TimingDecorator {

    private delegate

    private long accumulatedTime = 0

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

    TimingDecorator(delegate) {
        this.delegate = delegate
    }

    def invokeMethod(String name, args) {
        long beforeTime = System.currentTimeMillis()
        def result = delegate.invokeMethod(name, args)
        long duration = System.currentTimeMillis() - beforeTime

        if(methodInWhiteList(name)){
          accumulatedTime += duration
        }

        println "Calling $name($args) in ${duration} ms"

        result
    }
}