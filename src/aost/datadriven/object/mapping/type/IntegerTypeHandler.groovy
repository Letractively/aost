package aost.datadriven.object.mapping.type
/**
 * Handle Integer
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class IntegerTypeHandler implements TypeHandler{

    public valueOf(String s) {
        Integer.parseInt(s)
    }

}