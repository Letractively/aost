package org.telluriumsource.framework;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 30, 2010
 */
public class LookupData {
    private String id;
    private String className;
    private Object obj;

    public LookupData(String id, String className, Object obj) {
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
