package aost.datadriven.object.mapping.io

/**
 *
 * Read a field set from a stream, i.e., a file or a byte array
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface FieldSetReader {

    public List<String, String> readFieldSet(BufferedReader reader)

}