package org.tellurium.test

import org.tellurium.tool.UiParser

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 14, 2009
 * 
 */
class UiParser_UT extends GroovyTestCase{
    String data = """
        A | tag : table | /html/body/table[@id='mt']
        B | tag : th | /html/body/table[@id='mt']/tbody/tr/th[3]
        C | tag : div | /html/body/table[@id='mt']/tbody/tr/th[3]/div
        D | tag: div | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']
        E | tag: table, id: resultstable | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']
        F | tag: a | /html/body/div[@id='maincol']/div[@id='colcontrol']/div/div[@id='bub']/table[@id='resultstable']/tbody/tr[2]/td[3]/a
    """

    public void testParseData(){
        UiParser parser = new UiParser();
        parser.parseData(data);
    }

}