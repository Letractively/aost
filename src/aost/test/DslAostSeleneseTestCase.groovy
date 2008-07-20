package aost.test

import aost.connector.SeleniumConnector

/**
 * Used by the DSL Script Engine
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 */
class DslAostSeleneseTestCase extends AostSeleneseTestCase{
    
    protected SeleniumConnector connector

    public SeleniumConnector getConnector() {
        if(connector == null)
            return new SeleniumConnector()
        else
            return connector
    }

}