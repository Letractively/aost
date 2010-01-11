package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 6, 2010
 * 
 */

public class ChrisModule extends DslContext {

  public void defineUi(){
    ui.Container(uid: "ShipInfo", clocator: [tag: "div", class: "refer_m"]){
	  UrlLink(uid: "Update", clocator: [tag: "a", text: "Modify", id: "btn_consignee_edit"])
    }

    ui.List(uid: "breadcrumb", clocator: [tag: "ul", id: "breadcrumb"]){
      Container(uid: "all", clocator: [tag: "li"]){
          UrlLink(uid: "urlLink", clocator: [tag: "a", text: "Home"])
      }
    }

  }

  public void update(){
    click "ShipInfo.Update"
    pause 500
  }

  public static String HTML_BODY ="""
<div class="refer_m"><h4>Reciever Info　<span class="modify">
      <a id="btn_consignee_edit"  href="javascript:for_99click();">Modify</a></span></h4>
    <ul>
        <li>　Recipient name</li>
        <li>Address<span class="" id="country_name_display">China</span>
                   <span class="" id="province_name_display">Guangdong</span>
                   <span class="" id="city_name_display">Guangzhou</span>
                   <span id="town_name_display">A District</span>address
        </li>
        <li>ZIP 362100</li>
        <li>Tel 13777777777，12345678912</li>
        <li id="li_city_desc" class="objhide"></li>
    </ul>
</div>
  """
}