package org.tellurium.ddt.object.mapping.builder

import org.tellurium.ddt.object.mapping.FieldSet

/**
 * Build FieldSet from a collection of attributes
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldSetBuilder extends BaseBuilder{

    public FieldSet build(Map map) {
        map = makeCaseInsensitive(map)

        FieldSet fs = new FieldSet()
        fs.name = map.get(NAME)
        fs.description = map.get(DESCRIPTION)

        return fs
    }

    public FieldSet build(Map map, Closure closure){
       FieldSet fs = build(map)

       if(closure)
          closure(fs)

       return fs
   }

}