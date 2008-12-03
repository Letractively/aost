package org.tellurium.test.helper
/**
 * hold single parameter for evaulation
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 20, 2008
 * 
 */
class EvaulationAssertionValue extends AssertionValue{

    private static final String VALUE = "Value"
    private def value

    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(VALUE).append(avpSeparator).append("\"" + value + "\"").append(fieldSeparator)

        return sb.toString()
    }
}