package org.tellurium.test

import org.tellurium.dsl.DdDslContext

/**
 * Functional Test for GoogleDdDslContext
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class GoogleDdDslContext_FT extends GroovyTestCase{

    public void testGoogleSearch(){
        GoogleDdDslContext gddc = new GoogleDdDslContext()
        gddc.init()
        gddc.test()
        gddc.shutDown()
    }
}