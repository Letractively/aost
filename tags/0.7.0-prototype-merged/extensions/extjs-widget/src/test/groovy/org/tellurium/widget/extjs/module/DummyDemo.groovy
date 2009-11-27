package org.tellurium.widget.extjs.module

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 21, 2009
 * 
 */
class DummyDemo extends DslContext {
    public void defineUi() {
        ui.Container(uid: "test", clocator: [:]) {
            ExtJS_Dummy(uid: "dummy", clocator: [:])
        }
    }

    public String hello() {
        return onWidget("test.dummy", helloExtJSWidget)
    }
}