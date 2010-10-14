package org.telluriumsource.mock;


import org.telluriumsource.framework.*;


import org.telluriumsource.util.BaseUtil
import org.telluriumsource.framework.config.TelluriumConfigurator
import org.telluriumsource.framework.inject.Injector;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 20, 2010
 */
public class MockSessionFactory {

    public static Session getNewSession() {
        TelluriumConfigurator configurator = new TelluriumConfigurator();

        RuntimeEnvironment env = configurator.createDefaultRuntimeEnvironment();

        String name = Thread.currentThread().getName();
        name = name + "@" + BaseUtil.toBase62(System.currentTimeMillis());
        Session session = new Session();
        session.sessionId = name;
        session.env = env;
        session.beanFactory = Injector.instance;
      
        SessionManager.setSession(session);
        Injector.instance.assembleFramework(session);

        return session;
    }
}
