package org.telluriumsource.entity.config

import org.telluriumsource.entity.config.datadriven.DataProvider
import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 7, 2010
 * 
 * 
 */
class DataDriven {
  public String DATA_PROVIDER = "dataprovider";
  DataProvider dataProvider;

  def DataDriven() {
  }

  def DataDriven(Map map) {
    if (map != null) {
      Map m = map.get(DATA_PROVIDER);
      this.dataProvider = new DataProvider(m);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(DATA_PROVIDER, this.dataProvider?.toJSON());
    
    return obj;
  }
}
