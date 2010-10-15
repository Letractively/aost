package org.telluriumsource.framework;

import org.telluriumsource.inject.AbstractInjector
import org.telluriumsource.annotation.Injector

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 14, 2010
 */
@Injector
public class TelluriumInjector extends AbstractInjector {

    public String getCurrentSessionId(){
        return SessionManager.getSession().getSessionId();
    }
}
