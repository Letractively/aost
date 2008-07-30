package org.tellurium

import org.tellurium.datadriven.object.mapping.type.TypeHandlerRegistry
import org.tellurium.datadriven.object.mapping.FieldSetRegistry
import org.tellurium.datadriven.DataProvider
import org.tellurium.datadriven.object.mapping.FieldSetParser
import org.tellurium.datadriven.object.mapping.type.TypeHandlerRegistryConfigurator
import org.tellurium.datadriven.DataProvider
import org.tellurium.datadriven.DataProvider
import org.tellurium.datadriven.object.mapping.mapping.FieldSetMapResult

/**
 *
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class DataProvider_UT extends GroovyTestCase{
    protected TypeHandlerRegistry thr  = new TypeHandlerRegistry()
    protected FieldSetRegistry fsr = new FieldSetRegistry()

    protected DataProvider dataProvider = new DataProvider(fsr, thr)

    protected FieldSetParser fs = new FieldSetParser(fsr)

    public void setUp(){
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(thr, "phoneNumber", "org.tellurium.PhoneNumberTypeHandler")

        fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }
    }

    public void testFetchData(){
        String data = """
            true | 865-692-6000 | aost
            false| 865-123-4444 | aost selenium test
        """
//        dataProvider.start("src/example/dsl/googlesearchinput.txt")
        dataProvider.useString(data)
        
        FieldSetMapResult result = dataProvider.nextFieldSet()
        assertNotNull(result)
        assertFalse(result.isEmpty())
        boolean var1 = dataProvider.bind("regularSearch")
        def var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        String var3 = dataProvider.bind("fs4googlesearch.input")
        assertTrue(var1)
        assertEquals("8656926000", var2)
        assertEquals("aost", var3)
        result = dataProvider.nextFieldSet()
        var1 = dataProvider.bind("regularSearch")
        var2 = dataProvider.bind("fs4googlesearch.phoneNumber")
        var3 = dataProvider.bind("fs4googlesearch.input")
        assertFalse(var1)
        assertEquals("8651234444", var2)
        assertEquals("aost selenium test", var3)
        assertNotNull(result)
        assertFalse(result.isEmpty())
        result = dataProvider.nextFieldSet()
        assertNull(result)
        dataProvider.stop()
    }

    public void tearDown(){

    }

}