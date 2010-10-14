package org.telluriumsource.framework;

import org.telluriumsource.framework.inject.SessionQuery;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 13, 2010
 */
public class DefaultSessionQuery implements SessionQuery {

    public String getCurrentSessionId() {

        return SessionManager.getSession().getSessionId();
    }
}
