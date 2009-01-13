package org.tellurium.widget.dojo.object

import org.tellurium.widget.dojo.DojoWidget
import org.tellurium.dsl.WorkflowContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 12, 2009
 * 
 */
class JtvTabContainer extends DojoWidget{
    public static final String PLACE_HOLDER = "\\?";

    private List<String> tabList = new ArrayList<String>()

    private String groupLocate(){
        StringBuffer sb = new StringBuffer()
        for(String tab: tabList){
            sb.append(" and ").append("descendant::span[text()='${tab}']")
        }

        return sb.toString()
    }

    public void defineWidget() {
        String groupLocator = groupLocate()
        ui.Container(uid: "TabContainer", locator: "/div[contains(@id, 'jtv_widget_JtvTabContainer') and @wairole='tablist' ${groupLocator}]"){
            Container(uid: "dijitTab", locator: "/div[contains(@widgetid, 'jtv_widget_JtvTabButton') and contains(@class, 'dijitTab') and descendant::span[text()='?']]", respond: ["mouseOver", "mouseOut", "click"]){
                Container(uid: "innerTab", clocator: [tag: "div", class: "dijitTabInnerDiv", dojoattachpoint: "innerDiv"]){
                    Span(uid: "tab", clocator: [dojoattachpoint: "%%containerNode"])
                    Span(uid: "Close", clocator: [dojoattachpoint: "closeButtonNode", class: "closeImage"])
                }
            }
        }
    }

    //should override the click method to handle the place holder "?"
    protected void clickTab(String uid, String tab){
        WorkflowContext context = WorkflowContext.getDefaultContext()
        ui.walkTo(context, uid)?.click(){ loc, String[] events ->
            String locator = locatorMapping(context, loc)
            locator = locator.replaceFirst(PLACE_HOLDER, tab)
            eventHandler.click(locator, events)
        }
    }

    public void clickOnTab(String tabName){
        clickTab "TabContainer.dijitTab", tabName
    }
}