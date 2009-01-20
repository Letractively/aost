package org.tellurium.builder

import org.tellurium.builder.UiObjectBuilder
import org.tellurium.object.Table
import org.tellurium.object.UiObject

/**
 * Table builder
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 */

class TableBuilder extends UiObjectBuilder{

    protected static final String INCLUDE_TBODY = "tbody"

    def build(Map map, Closure closure) {
        //add default parameters so that the builder can use them if not specified
        def df = [:]
        df.put(TAG, Table.TAG)
        Table table = this.internBuild(new Table(), map, df)
        String includeTbody = map.get(INCLUDE_TBODY)
        if (includeTbody != null && FALSE.equalsIgnoreCase(includeTbody)) {
            table.tbody = ""
        }
        if (closure)
            closure(table)

        return table
    }

    def build(Table table, UiObject[] objects) {

        if (table == null || objects == null || objects.length < 1)
            return table

        objects.each {UiObject obj -> table.add(obj)}

        return table
    }

    def build(Table table, UiObject object) {

        if (table == null || object == null)
            return table

        table.add(object)

        return table
    }
}
