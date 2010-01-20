package org.telluriumsource.component.bundle

import org.telluriumsource.entity.EngineState
import org.telluriumsource.framework.Environment

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 19, 2010
 * 
**/

public class EngineStateTracer {
  private int numStateUpdate = 0;

  private EngineState state = new EngineState();

  public void callStateUpdate(){
    this.numStateUpdate++;
  }

  public boolean haveStateUpdate(){
    return this.numStateUpdate > 0;
  }

  public void cleanStateUpdate(){
    this.numStateUpdate = 0;    
  }

  public EngineState getEngineState(){
    return this.state;      
  }

  public void setEngineState(EngineState newstate){
    this.state = newstate;
  }

  public void setEngineState(boolean isUseCache, boolean isUseTeApi, boolean isUseClosestMatch){
    this.state.useCache(isUseCache);
    this.state.useTelluriumApi(isUseTeApi);
    this.state.useClosestMatch(isUseClosestMatch);
  }

  public void updateEngineStateFromEnvironment(){
    Environment env = Environment.instance;

    this.state.useCache(env.isUseCache());
    this.state.useTelluriumApi(env.isUseTelluriumApi());
    this.state.useClosestMatch(env.isUseClosestMatch());
  }
}