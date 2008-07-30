package org.tellurium.datadriven.object.mapping.builder

import org.tellurium.datadriven.object.mapping.ActionField

/**
 * 
 * Actioin Field builder
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 *
 */
class ActionFieldBuilder extends BaseBuilder{

    public ActionField build(Map map) {
        map = makeCaseInsensitive(map)

        ActionField f = new ActionField()
        f.name = map.get(NAME)
        f.description = map.get(DESCRIPTION)

        //do not override the default type
        if(map.get(TYPE) != null)
            f.type = map.get(TYPE)

        //action field cannot be null
        f.nullable = false

        if(map.get(LENGTH) != null)
            f.length = map.get(LENGTH)

        f.pattern = map.get(PATTERN)

        return f

    }
}