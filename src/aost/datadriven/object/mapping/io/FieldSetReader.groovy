package aost.datadriven.object.mapping.io

import aost.datadriven.object.mapping.FieldSet

/**
 *
 * Read field set from a stream, i.e., a file or a byte array
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface FieldSetReader {

     public Map<String, String> readFieldSet(FieldSet fieldSet,  BufferedReader reader)

}