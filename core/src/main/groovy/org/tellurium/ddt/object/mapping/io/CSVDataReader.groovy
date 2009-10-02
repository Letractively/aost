package org.tellurium.ddt.object.mapping.io

import org.tellurium.ddt.object.mapping.DataMappingException
import org.tellurium.i18n.InternationalizationManager;


/**
 * The implementation for the field set reader with comma-separated values
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 18, 2008
 * 
 */
class CSVDataReader implements DataReader{
		protected final static String FIELD_DELIMITER = ","
		//	protected final static String ESCAPE_START = "\\Q"
		//	protected final static String ESCAPE_END = "\\E"
		protected InternationalizationManager i18nManager = new InternationalizationManager();


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
				throw new DataMappingException(i18nManager.translate("DataReader.ReadDataException" , {e.getMessage()}))
			}
	
			return lst
		}
}