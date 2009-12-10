package org.telluriumsource.test

import org.telluriumsource.ddt.object.mapping.FieldSetRegistry
import org.telluriumsource.ddt.object.mapping.FieldSetParser
import org.telluriumsource.ddt.object.mapping.io.DataReader;
import org.telluriumsource.ddt.object.mapping.io.ExcelDataReader;

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class ExcelFieldSetReader_UT extends GroovyTestCase {


    void testReadData(){
        FieldSetRegistry fsr = new FieldSetRegistry()
        FieldSetParser parser = new FieldSetParser(fsr)
        parser.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
            Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
            Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
            Field(name: "input", description: "input variable")
        }

        assertNotNull(fsr)
        assertEquals(1, fsr.size())
        
        DataReader excelDataReader = new ExcelDataReader();
        excelDataReader.setupDataStream(new FileInputStream(new File(ClassLoader.getSystemResource("data/excelDataReaderTest.xls").getFile())))
        
        List<String> list = excelDataReader.readLineFromDataStream()
        assertNotNull(list)
        assertEquals(3, list.size())
        list = excelDataReader.readLineFromDataStream()
        assertNotNull(list)
        assertEquals(3, list.size())
        list = excelDataReader.readLineFromDataStream()
        assertTrue(list.isEmpty())
    }

}