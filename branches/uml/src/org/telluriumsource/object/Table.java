package org.telluriumsource.object;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Oct 1, 2009
 */
public class Table extends Container {
    public static final String TAG = "table";

    public String[] getAllTableCellText(){
        return null;
    }

    public int getTableHeaderColumnNumByXPath(){
        return 0;
    }

    public int getTableMaxRowNumByXPath(){
        return 0;
    }

    public int getTableMaxColumnNumByXPath(){
        return 0;
    }

    public int getTableHeaderColumnNumBySelector(){
       return 0;
    }

    public int getTableMaxRowNumBySelector(){
        return 0;
    }

    public int getTableMaxColumnNumBySelector(){
        return 0;
    }
}
