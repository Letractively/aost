package org.telluriumsource.ui.widget

import org.telluriumsource.dsl.UiID
import org.telluriumsource.dsl.WorkflowContext
import org.telluriumsource.ui.object.UiObject
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.dsl.UiDslParser
import org.telluriumsource.framework.SessionManager;


/**
 * The base class for Widget objects.
 *
 * Could be implemented using method delegation, but really ugly in that way.
 *
 * So we duplicate the code from BaseDslContext here until Groovy starts to support Mixin.
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 21, 2008
 *
 */
//@Mixin(BaseDslContext)
abstract class Widget extends UiObject {
  protected IResourceBundle i18nBundle;

  public final static String NAMESPACE_SUFFIX = "_";

  @Delegate
  protected WidgetDslContext dsl;

  UiDslParser ui;

  //Note:
  //we need namespace to differentiate the same widget name from different widget modules
  //for example, if Dojo and ExtJS both has the widget called Accordion, we have to differentiate
  //them using name space, i.e., DOJO::Accordion and ExtJS::Accordion
  public Widget(){
      dsl = new WidgetDslContext();
      dsl.locator = this.locator;
      ui = dsl.ui;
  }

  abstract public void defineWidget();


  //walkTo through the object tree to until the Ui Object is found by the UID
  public UiObject walkTo(WorkflowContext context, UiID uiid) {
    //if not child listed, return itself
    if (uiid.size() < 1)
      return this

    //otherwise,
    //check if the uid is the same as the uiid
    //if it is, return itself
    //otherwise, return null since widget is atomic and should not any other ui object

    String nextid = (String) uiid.pop()
    if (nextid.equalsIgnoreCase(this.uid)) {
      return this
    } else {
      IResourceBundle i18nBundle = SessionManager.getSession().getLookup().lookById("i18nBundle");
      println(i18nBundle.getMessage("Widget.CannotFindUIObject" , nextid , this.uid ))
      return null
    }
  }
}