package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.mapping.type.TypeHandler
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.CharacterCodingException
import static org.telluriumsource.util.Helper.parseUnicode

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2010
 * 
 */
class UnicodeTypeHandler implements TypeHandler {

  public String valueOf(String s) {
    if(s == null || s.trim().length() == 0){
      return s;
    }
    return parseUnicode(s);
    
/*    StringBuffer sb = new StringBuffer();
    String str = s;
    int i = str.indexOf("\\u");
    while(i != -1){
      sb.append(str.substring(0, i));
      char unicode = (char)Integer.parseInt(str.substring(i+3, i+6), 16);
//      sb.append(unicode.toString().getBytes("ISO-8859-1"));
      sb.append(unicode);
      str = str.substring(i+6);
      i = str.indexOf("\\u");
    }

    return sb.toString();*/
  }
}