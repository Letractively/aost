package org.telluriumsource.entity.uiobject

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class Builder {
  Map<String, String> map;

  def Builder() {
  }

  def Builder(Map map) {
    this.map = map;
  }

  public JSONObject toJSON() {
    JSONObject obj = new JSONObject();
    if (this.map != null && this.map.size() > 0) {
      Set<String> keys = this.map.keySet();
      for (String key: keys) {
        obj.put(key, this.map.get(key));
      }
    }

    return obj;
  }
  
  public void toProperties(Properties properties, String path){
    if(map != null && !map.isEmpty()){
      map.each {String key, String val ->
        properties.setProperty(path + "." + key, val);
      }
    }

  }
}
