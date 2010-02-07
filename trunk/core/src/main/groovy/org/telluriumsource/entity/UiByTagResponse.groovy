package org.telluriumsource.entity

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Feb 7, 2010
 * 
 */
class UiByTagResponse {
  //tag name
  String tag;

  //better not to use it when generating locator
  int index;

  //UID, if not set, use tid
  String uid;

  //temporally assigned ID
  String tid;
}
