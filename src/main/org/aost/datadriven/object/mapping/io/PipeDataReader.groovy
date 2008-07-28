package org.aost.datadriven.object.mapping.io

import org.aost.datadriven.object.mapping.DataMappingException

/**
 * The implementation for the field set reader with pipe field delimiter
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class PipeDataReader implements DataReader{
	protected final static String FIELD_DELIMITER = "\\|"
	protected final static String ERROR_DATA_READ_EXCEPTION = "Read data exception"

    //read in a line from a file and then convert them to a String list
    public List readLine(BufferedReader reader) {

		List<String, String> lst = new ArrayList<String, String>()
        
        try {
			String line = reader.readLine()
			//If we reached the end of the file, no more lines, just return.
			if(line == null)
				return lst

			String[] fields = line.split(FIELD_DELIMITER)

            for(String s : fields){
                lst.add(s.trim())
            }

		} catch (IOException e) {
			throw new DataMappingException(ERROR_DATA_READ_EXCEPTION + " " + e.getMessage())
		}

		return lst
	}
}