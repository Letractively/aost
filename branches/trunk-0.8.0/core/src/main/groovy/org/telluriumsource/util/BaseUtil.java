package org.telluriumsource.util;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 11, 2010
 */
public class BaseUtil {

    private static String alphabet="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String toBase(long number, int base){
        if(number < base){
            return Character.toString(alphabet.charAt((int)number));
        }

        long value = number;
        StringBuffer sb = new StringBuffer();

        while(value != 0){
            int remind = (int) (value % base);
            value = (value - remind)/base;
            sb.append(alphabet.charAt(remind));
        }

        return sb.toString();
    }

    public static String toBase36(long number){
        return toBase(number, 36);
    }

    public static String toBase62(long number){
        return toBase(number, 62);
    }
}
