package org.telluriumsource.test.report
import org.telluriumsource.framework.Environment;
import org.telluriumsource.crosscut.i18n.IResourceBundle
import org.telluriumsource.framework.SessionManager;


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
//    protected IResourceBundle i18nBundle


    public EvaulationAssertionValue(){
//      SessionManager.getSession().getI18nBundle() = SessionManager.getSession().getLookup().lookById("SessionManager.getSession().getI18nBundle()");
    }
  
    public String toString() {
        final int typicalLength = 64
        final String avpSeparator = ": "
        final String fieldSeparator = ","
        final String fieldStart = " "

        StringBuilder sb = new StringBuilder(typicalLength)
        sb.append(SessionManager.getSession().getI18nBundle().getMessage("EvaulationAssertionValue.Expected")).append(avpSeparator).append("\"" + value + "\"").append(fieldSeparator)

        return sb.toString()
    }
}