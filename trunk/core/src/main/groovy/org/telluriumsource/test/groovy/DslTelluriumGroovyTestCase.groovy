package org.telluriumsource.test.groovy

import org.telluriumsource.layer.connector.SeleniumConnector

/**
 * Used by the DSL Script Engine
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class DslTelluriumGroovyTestCase extends BaseTelluriumGroovyTestCase{
    
//    protected SeleniumConnector conn

    public SeleniumConnector getConnector() {
        if(conn == null)
            return new SeleniumConnector()
        else
            return conn
    }


    public void testAlwaysSucceeds() {
    }
}