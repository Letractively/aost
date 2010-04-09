package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Bundle {
  public static String MAX_MACRO_CMD = "maxMacroCmd";
  int maxMacroCmd = 5;
  
  //false means  maxMacroCmd = 1
  public static String USE_MACRO_COMMAND = "useMacroCommand";
  boolean useMacroCommand = true;


  def Bundle() {
  }

  def Bundle(Map map) {
    this.maxMacroCmd = map.get(MAX_MACRO_CMD);
    this.useMacroCommand = map.get(USE_MACRO_COMMAND);
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(MAX_MACRO_CMD, this.maxMacroCmd);
    obj.put(USE_MACRO_COMMAND, this.useMacroCommand);

    return obj;
  }

}
