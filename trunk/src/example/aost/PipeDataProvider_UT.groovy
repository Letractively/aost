package example.aost

import aost.datadriven.object.mapping.type.TypeHandlerRegistry
import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.PipeDataProvider
import aost.datadriven.object.mapping.FieldSetParser
import aost.datadriven.object.mapping.type.TypeHandlerRegistryConfigurator


/**
 *
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class PipeDataProvider_UT extends GroovyTestCase{
    protected TypeHandlerRegistry thr  = new TypeHandlerRegistry()
    protected FieldSetRegistry fsr = new FieldSetRegistry()

    protected PipeDataProvider dataProvider = new PipeDataProvider(fsr, thr)

    protected FieldSetParser fs = new FieldSetParser(fsr)

    public void setUp(){
        TypeHandlerRegistryConfigurator.addCustomTypeHandler(thr, "phoneNumber", "example.aost.PhoneNumberTypeHandler")

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
        dataProvider.use(data)
        
        boolean result = dataProvider.nextFieldSet()
        assertTrue(result)
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
        assertTrue(result)
        result = dataProvider.nextFieldSet()
        assertFalse(result)
        dataProvider.stop()
    }

    public void tearDown(){

    }

}