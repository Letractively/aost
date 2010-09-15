package org.telluriumsource.dsl


import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.telluriumsource.entity.UiModuleValidationResponse
import org.stringtree.json.JSONReader
import org.telluriumsource.framework.RuntimeEnvironment
import org.telluriumsource.component.custom.Extension
import org.telluriumsource.entity.EngineState
import org.telluriumsource.component.data.Accessor
import org.telluriumsource.component.event.EventHandler

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
abstract class BaseDslContext implements DslContract {

  protected IResourceBundle i18nBundle;

  protected UiDslParser ui;

  protected RuntimeEnvironment env;

  protected EventHandler eventHandler

  protected Accessor accessor

  protected Extension extension;

  protected geti18nBundle() {
    return this.i18nBundle;
  }

  protected JSONReader reader = new JSONReader()

  protected UiModuleValidationResponse parseUseUiModuleResponse(String result) {
    String out = result;
    if (result.startsWith("OK,")) {
      out = result.substring(3);
    }

    if(out.length() > 0){
      Map map = reader.read(out);
      UiModuleValidationResponse response = new UiModuleValidationResponse(map);

      return response;
    }

    return null;
  }

//  protected UiModuleValidationResponse parseUseUiModuleResponse(Map result) {
  protected Map parseUseUiModuleResponse(Map result) {
    return result;
  }

  protected String getUiModuleId(String uid) {
    UiID uiid = UiID.convertToUiID(uid);
    return uiid.pop();
  }

  public EngineState getEngineState() {
    WorkflowContext context = WorkflowContext.getDefaultContext();
    Map map = extension.getEngineState(context);

    EngineState state = new EngineState();
    state.parseJson(map);

    return state;
  }

  public void helpTest() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.helpTest(context);
  }

  public void noTest() {
    WorkflowContext context = WorkflowContext.getDefaultContext();

    extension.noTest(context);
  }
}