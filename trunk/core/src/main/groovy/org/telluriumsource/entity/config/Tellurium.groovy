package org.telluriumsource.entity.config

import org.json.simple.JSONObject

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 8, 2010
 * 
 */
class Tellurium {
  public static String EMBEDDED_SERVER = "embeddedServer";
  EmbeddedServer embeddedServer;

  public static String EVENT_HANDLER = "eventHandler";
  EventHandler eventHandler;

  public static String ACCESSOR = "accessor";
  Accessor accessor;

  public static String BUNDLE = "bundle";
  Bundle bundle;

  public static String CONNECTOR = "connector";
  Connector connector;

  public static String DATA_DRIVEN = "datadriven";
  DataDriven dataDriven;

  public static String TEST = "test";
  Test test;

  public static String UIOBJECT = "uiobject";
  UiObject uiobject;

  public static String WIDGET = "widget";
  Widget widget;

  def Tellurium() {
  }

  def Tellurium(Map map) {
    if (map != null) {
      Map es = map.get(EMBEDDED_SERVER);
      if (es != null)
        this.embeddedServer = new EmbeddedServer(es);

      Map eh = map.get(EVENT_HANDLER);
      if (eh != null)
        this.eventHandler = new EventHandler(eh);

      Map ac = map.get(ACCESSOR);
      if (ac != null)
        this.accessor = new Accessor(ac);

      Map bu = map.get(BUNDLE);
      if (bu != null)
        this.bundle = new Bundle(bu);

      Map cn = map.get(CONNECTOR);
      if (cn != null)
        this.connector = new Connector(cn);

      Map dd = map.get(DATA_DRIVEN);
      if (dd != null)
        this.dataDriven = new DataDriven(dd);

      Map tt = map.get(TEST);
      if (tt != null)
        this.test = new Test(tt);

      Map uo = map.get(UIOBJECT);
      if (uo != null)
        this.uiobject = new UiObject(uo);

      Map wg = map.get(WIDGET);
      if (wg != null)
        this.widget = new Widget(wg);
    }
  }

  public JSONObject toJSON(){
    JSONObject obj = new JSONObject();
    obj.put(EMBEDDED_SERVER, this.embeddedServer?.toJSON());
    obj.put(EVENT_HANDLER, this.eventHandler?.toJSON());
    obj.put(ACCESSOR, this.accessor?.toJSON());
    obj.put(BUNDLE, this.bundle?.toJSON());
    obj.put(CONNECTOR, this.connector?.toJSON());
    obj.put(DATA_DRIVEN, this.dataDriven?.toJSON());
    obj.put(TEST, this.test?.toJSON());
    obj.put(UIOBJECT, this.uiobject?.toJSON());
    obj.put(WIDGET, this.widget?.toJSON());

    return obj;
  }

  public void getDefault(){
    this.embeddedServer = new EmbeddedServer();
    this.eventHandler = new EventHandler();
    this.accessor = new Accessor();
    this.bundle = new Bundle();
    this.connector = new Connector();
    this.dataDriven = new DataDriven();
    this.dataDriven.getDefault();
    this.test = new Test();
    this.test.getDefault();
    this.uiobject = new UiObject();
    this.uiobject.getDefault();
    this.widget = new Widget();
    this.widget.getDefault();
  }
}
