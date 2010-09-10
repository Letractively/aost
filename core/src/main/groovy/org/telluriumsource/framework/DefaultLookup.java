package org.telluriumsource.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 10, 2010
 */
public class DefaultLookup implements Lookup {
    private List<LookupData> list = new ArrayList<LookupData>();
    private Map<String, LookupData> index = new HashMap<String, LookupData>();

    public void register(String id, Object obj){
        String className = obj.getClass().getCanonicalName();
        LookupData data = new LookupData(id, className, obj);
        list.add(data);
        index.put(id, data);
    }

    public void clear(){
        list = new ArrayList<LookupData>();
        index = new HashMap<String, LookupData>();
    }

    public Object lookById(String id) {
        LookupData data = index.get(id);
        if(data != null)
            return data.getObj();
        
        return null;
    }

    public List lookByClass(String className) {
        List cl = new ArrayList();
        for(LookupData data: list){
            if(data.getClassName().equals(className)){
                cl.add(data.getObj());
            }
        }

        return cl;
    }

    class LookupData {

        String id;
        String className;
        Object obj;

        LookupData(String id, String className, Object obj) {
            this.id = id;
            this.className = className;
            this.obj = obj;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }
    }
}
