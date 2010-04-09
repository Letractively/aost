package org.telluriumsource.entity.config

import org.telluriumsource.entity.config.test.Execution
import org.telluriumsource.entity.config.test.Result
import org.telluriumsource.entity.config.test.Exception
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Test {
  public static String EXECUTION = "execution";
  Execution execution;

  public static String RESULT = "result";
  Result result;

  public static String EXCEPTION = "exception";
  org.telluriumsource.entity.config.test.Exception exception;

  def Test() {
  }

  def Test(Map map) {
    Map mex = map.get(EXECUTION);
    this.execution = new Execution(mex);

    Map mre = map.get(RESULT);
    this.result = new Result(mre);

    Map mec = map.get(EXCEPTION);
    this.exception = new org.telluriumsource.entity.config.test.Exception(mec);
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(EXECUTION, this.execution.toJSON());
    obj.put(RESULT, this.result.toJSON());
    obj.put(EXCEPTION, this.exception.toJSON());

    return obj;
  }
}

