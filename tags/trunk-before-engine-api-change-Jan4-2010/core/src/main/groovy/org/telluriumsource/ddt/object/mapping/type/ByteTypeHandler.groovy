package org.telluriumsource.ddt.object.mapping.type
/**
 * Handle Byte
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class ByteTypeHandler implements TypeHandler {

    public valueOf(String s) {
       return Byte.parseByte(s)
    }

}