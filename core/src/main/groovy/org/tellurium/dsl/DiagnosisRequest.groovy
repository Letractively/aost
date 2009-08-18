package org.tellurium.dsl

import org.tellurium.locator.CompositeLocator
import org.json.simple.JSONObject

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2009
 *
 */

public class DiagnosisRequest {

  public static final String REQUEST = "request"
  public static final String TAG = "tag"
  public static final String TEXT = "text"

  public static final String UID = "uid"
  private String uid;

  public static final String PARENT_LOCATOR = "plocator"
  private String pLocator;

  public static final String ATTRIBUTES = "attributes"
  private Map<String, String> attributes;

  public static final String RETURN_HTML = "retHtml"
  private boolean retHtml;

  public static final String RETURN_PARENT = "retParent"
  private boolean retParent;

  def DiagnosisRequest(String uid, String pLocator, locator, DiagnosisOption options) {
    this.uid = uid;
    this.pLocator = pLocator;
    if(locator != null && locator instanceof CompositeLocator){
      this.attributes = new HashMap<String, String>();
      this.attributes.put(TAG, locator.tag);
      if(locator.text != null && locator.text.size()){
        this.attributes.put(TEXT, locator.text); 
      }

      if(locator.attributes.size() > 0){
        this.attributes.putAll(locator.attributes);
      }
    }

    this.retHtml = options.getRetHtml();
    this.retParent = options.retParent();
  }

  public String toJson(){
    JSONObject obj = new JSONObject();
    obj.put(UID, this.uid);
    obj.put(PARENT_LOCATOR, this.pLocator);
    JSONObject attrs = new JSONObject();
    if(this.attributes != null && this.attributes.size() > 0){
      this.attributes.each {String key, String val->
        attrs.put(key, val);
      }
    }

    obj.put(ATTRIBUTES, attrs);
    obj.put(RETURN_HTML, retHtml);
    obj.put(RETURN_PARENT, retParent)

    return obj.toString()
  }
}