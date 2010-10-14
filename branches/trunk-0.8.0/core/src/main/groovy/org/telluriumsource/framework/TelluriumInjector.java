package org.telluriumsource.framework;

import org.telluriumsource.annotation.Inject;
import org.telluriumsource.annotation.Provider;
import org.telluriumsource.framework.inject.Injector;
import org.telluriumsource.framework.inject.SessionQuery;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 14, 2010
 */
@Inject
@Provider(type=Injector.class)
public class TelluriumInjector extends Injector {
    
    private SessionQuery sQuery = new DefaultSessionQuery();

    public SessionQuery getSessionQuery(){
        return this.sQuery;

    }
}
