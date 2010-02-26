package org.telluriumsource.udl;

import org.json.simple.JSONObject;

import java.rmi.server.UID;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 17, 2010
 *
 */
public class MetaData {
    public static String ID = "id";
    protected String id;

    public MetaData() {
    }

    public MetaData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject toJSON() {
        JSONObject jso = new JSONObject();
        jso.put(ID, this.id);
        return jso;
    }

}
