package org.tellurium;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Sep 29, 2009
 */

@XmlType(name = "EType", namespace = "http://org.telluriumsource")
@XmlEnum(String.class)
public enum EType {
	@XmlEnumValue("A") A("A"),
	@XmlEnumValue("B") B("B"),
	@XmlEnumValue("C") C("C");

    private final String value;

    EType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

}
