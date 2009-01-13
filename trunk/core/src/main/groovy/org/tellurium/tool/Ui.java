package org.tellurium.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 13, 2009
 */
public class Ui {

    private static Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("div", "DIV");
        map.put("a", "UrlLink");
        map.put("input", "Button");
        map.put("img", "Image");
        map.put("select", "Selector");
        map.put("form", "Form");
        map.put("table", "Table");
    }

    public static String getType(String tag, boolean hasChildren){
        String type = map.get(tag);
        if(map.get(tag) != null){
            return type;
        }else{
            return "TextBox";
        }
    }
}
