package org.telluriumsource.framework;

import org.telluriumsource.annotation.Inject;
import org.telluriumsource.annotation.Provider;
import org.telluriumsource.framework.inject.Injector;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 14, 2010
 */
@Inject
@Provider
public class TelluriumInjector extends Injector {

    public String getCurrentSessionId(){
        return SessionManager.getSession().getSessionId();
    }
}
