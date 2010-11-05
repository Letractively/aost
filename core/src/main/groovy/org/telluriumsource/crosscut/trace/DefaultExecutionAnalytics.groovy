package org.telluriumsource.crosscut.trace

import org.telluriumsource.annotation.Provider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */

@Provider(name="DefaultExecutionAnalytics")
public class DefaultExecutionAnalytics implements ExecutionAnalytics, ExecutionReporter {
  private static int SB_LEN = 64;

  private long startTime = 0;

  private long endTime = 0;

  private Map<String, ExecutionResult> map = new HashMap<String, ExecutionResult>();

  public void analyze(ExecutionTime exeTime) {

    long ltime = exeTime.startTime + exeTime.duration;
    if(endTime < ltime){
      endTime = ltime;
    }
    if(startTime == 0)
      startTime = exeTime.startTime;

    ExecutionResult result = map.get(exeTime.testName);
    if(result != null){
      ++result.count;
      result.total += exeTime.duration;
    }else{
      result = new ExecutionResult(exeTime.testName, 1, exeTime.duration);
    }

    map.put(exeTime.testName, result);
  }

  def report() {
    StringBuffer sb = new StringBuffer(SB_LEN);
    sb.append("Start Time: ").append(startTime).append("\n")
      .append("End Time: ").append(endTime).append("\n")
      .append("Total Runtime: ").append(endTime-startTime).append("ms\n");
    map.each {String key, ExecutionResult value ->
      sb.append("Name: ").append(value.name).append(", count: ").append(value.count)
              .append(", total: ").append(value.total).append("ms, average: ");
      int average = value.total/value.count;
      sb.append(average).append("ms\n");
    }

    return sb.toString();
  }
}