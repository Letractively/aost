package org.tellurium.test.helper
import org.tellurium.i8n.InternationalizationManager

/**
 * hold single parameter for evaulation
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 20, 2008
 * 
 */
class EvaulationAssertionValue extends AssertionValue{

    private def value
    protected InternationalizationManager i8nManager = new InternationalizationManager()


    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(i8nManager.translate("EvaulationAssertionValue.Expected")).append(avpSeparator).append("\"" + value + "\"").append(fieldSeparator)

        return sb.toString()
    }
}