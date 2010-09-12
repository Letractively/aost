package org.telluriumsource.dsl


import org.telluriumsource.crosscut.i18n.IResourceBundle;

import org.telluriumsource.entity.UiModuleValidationResponse
import org.stringtree.json.JSONReader

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
abstract class BaseDslContext implements DslContract {

  protected IResourceBundle i18nBundle

  protected UiDslParser ui

//  public BaseDslContext(){
//	  i18nBundle = Environment.instance.myResourceBundle()
//  }

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
}