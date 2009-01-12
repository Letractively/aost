package org.tellurium.widget.dojo.builder

import org.tellurium.builder.UiObjectBuilder
import org.tellurium.widget.dojo.object.JtvTabContainer

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 12, 2009
 * 
 */
class JtvTabContainerBuilder extends UiObjectBuilder{
    public static final String TAB_NAMES = "tabs"

    public build(Map map, Closure c) {
       //add default parameters so that the builder can use them if not specified
        def df = [:]
        JtvTabContainer tabContainer = this.internBuild(new JtvTabContainer(), map, df)
        tabContainer.tabList = map.get(TAB_NAMES)
        tabContainer.defineWidget()

        return tabContainer
    }

}