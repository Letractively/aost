package org.telluriumsource.crosscut.trace

import org.telluriumsource.crosscut.log.ConsoleAppender
import org.telluriumsource.crosscut.log.SimpleLogger
import org.telluriumsource.crosscut.log.Logger
import org.telluriumsource.annotation.Provider
import org.telluriumsource.annotation.Inject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 21, 2009
 * 
 */
@Provider(type=ExecutionTracer.class)
public class DefaultExecutionTracer implements ExecutionTracer{

  private Logger logger;

  @Inject(name="DefaultExecutionAnalytics")
  private ExecutionAnalytics analytics;

  @Inject(name="DefaultExecutionAnalytics")
  private ExecutionReporter reporter;

  def DefaultExecutionTracer() {
     logger = SimpleLogger.instance;
     logger.addAppender(new ConsoleAppender());
//     analytics = new DefaultExecutionAnalytics();
//     reporter = analytics;
  }

  public void publish(String testName, long start, long duration) {

    analytics.analyze(new ExecutionTime(testName, start, duration));
    logger.log("Name: ${testName}, start: ${start}, duration: ${duration}ms");
  }

  public void log(String message){
    logger.log(message);
  }
  
  public void warn(String message){
    logger.log("WARNING: " + message);  
  }

  def report() {
    String result = reporter.report();
    logger.log(result);
  }
}