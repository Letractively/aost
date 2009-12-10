package org.telluriumsource.test

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