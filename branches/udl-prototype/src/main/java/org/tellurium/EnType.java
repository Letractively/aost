package org.tellurium;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Sep 29, 2009
 */
@XmlType(name = "EnType", namespace = "http://org.telluriumsource")
@XmlEnum(String.class)
public enum EnType {
    @XmlEnumValue("A") A,
    @XmlEnumValue("B") B,
    @XmlEnumValue("C") C 
}
