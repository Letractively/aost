package org.telluriumsource.ut

import org.telluriumsource.module.GoogleSearchModule

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 31, 2009
 *
 */

public class UiModuleJSonConverter_UT extends GroovyTestCase {

  public void testToJson(){
    GoogleSearchModule gsm = new GoogleSearchModule();
    gsm.defineUi();
/*
    def obj = gsm.getUiElement("Google");
    JSONObject jso = new JSONObject()
    jso.put("Object", obj);
*/
    println gsm.jsonify("Google");
  }
}
