package org.telluriumsource.ddt

import org.telluriumsource.test.ddt.mapping.type.TypeHandler
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.nio.charset.CharsetEncoder
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.CharacterCodingException

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2010
 * 
 */
class UnicodeTypeHandler implements TypeHandler {

  public String valueOf(String s) {
    StringBuffer sb = new StringBuffer();
    String str = s;
    int i = str.indexOf("\\u");
    while(i != -1){
      sb.append(str.substring(0, i));
      Integer unicode = Integer.parseInt(str.substring(i+3, i+6), 16);
//      sb.append(unicode.toString().getBytes("ISO-8859-1"));
      sb.append(new String(unicode.toString().getBytes("ISO-8859-1"), "utf-8"));
      str = str.substring(i+6);
      i = str.indexOf("\\u");
    }

    s = sb.toString();

    Charset charset = Charset.forName("ISO-8859-1");
    Charset charsetutf8 = Charset.forName("UTF-8");
    CharsetDecoder decoder = charset.newDecoder();
    CharsetEncoder encoder = charsetutf8.newEncoder();
    String value = new String(s.getBytes(), "ISO-8859-1");
    value = new String(s.getBytes("ISO-8859-1"), "utf-8");

    try {
      ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(s));
      CharBuffer cbuf = decoder.decode(bbuf);
      String val = cbuf.toString();
      val;
    } catch (CharacterCodingException e) { }

  }
}