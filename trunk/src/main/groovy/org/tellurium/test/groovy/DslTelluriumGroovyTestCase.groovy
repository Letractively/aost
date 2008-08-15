package org.tellurium.test.groovy

import org.tellurium.connector.SeleniumConnector

/**
 * Used by the DSL Script Engine
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class DslTelluriumGroovyTestCase extends BaseTelluriumGroovyTestCase{
    
    protected SeleniumConnector connector

    public SeleniumConnector getConnector() {
        if(connector == null)
            return new SeleniumConnector()
        else
            return connector
    }

}