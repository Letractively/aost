package org.tellurium.test

import org.tellurium.connector.SeleniumConnector

/**
 * Used by the DSL Script Engine
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class DslTelluriumTestCase extends TelluriumTestCase{
    
    protected SeleniumConnector connector

    public SeleniumConnector getConnector() {
        if(connector == null)
            return new SeleniumConnector()
        else
            return connector
    }

}