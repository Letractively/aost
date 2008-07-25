package example.aost

import aost.datadriven.object.mapping.FieldSetRegistry
import aost.datadriven.object.mapping.FieldSetParser
import aost.datadriven.object.mapping.FieldSet
import aost.datadriven.object.mapping.io.PipeFieldSetReader

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class PipeFieldSetReader_UT extends GroovyTestCase {

    protected String data = """true | 865-692-6000 | aost
       false| 865-123-4444 | aost selenium test"""

    void testReadData(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(id: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
        FieldSet fs = fsr.getFieldSet("fs4googlesearch")

        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        PipeFieldSetReader reader = new PipeFieldSetReader()
        Map<String, String> map = reader.readFieldSet(fs, br)
        assertNotNull(map)
        assertEquals(3, map.size())
        map = reader.readFieldSet(fs, br)
        assertNotNull(map)
        assertEquals(3, map.size())
        map = reader.readFieldSet(fs, br)
        assertTrue(map.isEmpty())
    }

}