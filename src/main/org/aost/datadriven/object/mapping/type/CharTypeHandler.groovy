package org.aost.datadriven.object.mapping.type
/**
 * Handler Char type
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class CharTypeHandler implements TypeHandler {

    public valueOf(String s) {
         return s.charAt(0)
    }

}