package org.telluriumsource.test.helper
import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;


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
    protected InternationalizationManager i18nManager = new InternationalizationManagerImpl()


    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(i18nManager.translate("EvaulationAssertionValue.Expected")).append(avpSeparator).append("\"" + value + "\"").append(fieldSeparator)

        return sb.toString()
    }
}