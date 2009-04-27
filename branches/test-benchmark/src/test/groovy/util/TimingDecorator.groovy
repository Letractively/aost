package util

import com.thoughtworks.selenium.SeleniumException

public class TimingDecorator {

    private delegate

    private long startTime = -1

    private long endTime = -1

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
      this.startTime = -1
      this.endTime = -1
    }

    public long getStartTime(){
      return this.startTime
    }

    public long getEndTime(){
      return this.endTime
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
        if(startTime == -1)
          startTime = System.currentTimeMillis()
        long beforeTime = System.currentTimeMillis()
        def result = delegate.invokeMethod(name, args)
/*        try{
          result = delegate.invokeMethod(name, args)
        }catch(SeleniumException e){
//          e.getStackTrace().dump();
          this.delegate.cleanSelectorCache();
          result = delegate.invokeMethod(name, args)
        }*/
      
        long duration = System.currentTimeMillis() - beforeTime

        endTime = System.currentTimeMillis()
        if(methodInWhiteList(name)){
          accumulatedTime += duration
          println "Calling $name($args) in ${duration} ms <-- Accumulated "
        }else{
          println "Calling $name($args) in ${duration} ms"
        }
        result
    }
}