package org.tellurium.tool;

import org.tellurium.i18n.InternationalizationManager;

/**
 * Match two XPaths and return the common portion
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 10, 2008
 */
public class XPathMatcher {
    public final static String DELIMITER = "/";
    
    protected static InternationalizationManager i18nManager = new InternationalizationManager();


    public static String match(String src, String dst){
        if(src == null || dst == null)
            return null;

        //try to find the shorter one from the two xpaths
        int minlen = src.split(DELIMITER).length;
        String shorter = src;
        String longer = dst;
        int longlen = dst.split(DELIMITER).length;
        if(minlen > longlen){
            minlen = longlen;
            shorter = dst;
            longer = src;
        }

        String[] shortsplit = shorter.split(DELIMITER);
        String[] longsplit = longer.split(DELIMITER);
        StringBuffer sb = new StringBuffer(minlen);

        for(int i=0; i<minlen; i++){
            if(shortsplit[i].equals(longsplit[i])){
                //match
                if(i>0)
                    sb.append(DELIMITER);        
                sb.append(shortsplit[i]);
            }else
                //not match
                break;
        }

        return sb.toString();
    }

    public static String remainingXPath(String original, String prefix){
        if(original == null || prefix == null)
            return original;
        if(original.startsWith(prefix)){
            return original.substring(prefix.length());
        }

        throw new RuntimeException(i18nManager.translate("XPathMatcher.RunTimeException" , new Object[] {original , prefix}));
    }
}
