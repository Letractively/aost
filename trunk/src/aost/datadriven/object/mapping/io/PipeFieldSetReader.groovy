package aost.datadriven.object.mapping.io

import aost.datadriven.object.mapping.FieldSet
import aost.datadriven.object.mapping.DataMappingException
import aost.datadriven.object.mapping.Field
import java.util.regex.Pattern
import java.util.regex.Matcher

/**
 * The implementation for the field set reader with pipe field delimiter
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class PipeFieldSetReader implements FieldSetReader{
	protected final static String FIELD_DELIMITER = "\\|";
	protected final static String ERROR_INVALID_FIELD_SET = "Invalid field set";
	protected final static String ERROR_DATA_READ_EXCEPTION = "Read data exception";
	protected final static String ERROR_FIELD_SET_SIZE_NOT_MATCH = "Field set size does not match";
	protected final static String ERROR_INVALID_FIELD_VALUE = "Invalid field value ";
	protected final static String ERROR_FIELD_CANNOT_BE_NULL = "Field cannot be null";
	protected final static String ERROR_FIELD_PATTERN_NOT_MATCH = "Field does not match pattern ";

	public Map<String, String> readFieldSet(FieldSet fieldSet, BufferedReader reader) {
		if(fieldSet == null || fieldSet.getFields() == null || fieldSet.getFields().size() < 1)
			throw new DataMappingException(ERROR_INVALID_FIELD_SET);

		Map<String, String> dfm = new HashMap<String, String>();
		int size = fieldSet.getFields().size();
		try {
			String line = reader.readLine();
			//If we reached the end of the file, no more lines, just return.
			if(line == null)
				return dfm;

			String[] fields = line.split(FIELD_DELIMITER);
			if(fields == null || fields.length != size)
				throw new DataMappingException(ERROR_FIELD_SET_SIZE_NOT_MATCH);

			for(int i=0; i<size; i++){
				String trimed = fields[i].trim();
				Field df = fieldSet.getFields().get(i);
				//check if the field is empty
				if(trimed.length() == 0){
					//use the null value to replace the empty field
					if(df.getNullValue() != null)
						trimed = df.getNullValue();
				}
				//check field length, if the length is specified, need to keep the last "LENGTH" characters
				if(df.getLength() > 0 && df.getLength() < trimed.length()){
					trimed = trimed.substring(trimed.length() - df.getLength());
				}

				//Check if the field can be null
				if(trimed.length() == 0 && (!df.isNullable()))
					throw new DataMappingException(ERROR_FIELD_CANNOT_BE_NULL);

				//only check the pattern if the field is not empty
				if(df.getPattern() != null && trimed.length() > 0 ){
					//TODO:For efficient, we may need to move the pattern to data field class so that we do not need
					//to compile it each time.
					Pattern pattern = Pattern.compile(df.getPattern());
					Matcher matcher = pattern.matcher(trimed);
					//if not match
					if(!matcher.matches())
						throw new DataMappingException(ERROR_FIELD_PATTERN_NOT_MATCH + df.getPattern());
				}

				//If custom validator is defined and the field is not empty, we need to call it
//				if(df.getValidator() != null && trimed.length() > 0){
//					if(!df.getValidator().validate(trimed))
//						throw new DataMappingException(ERROR_INVALID_FIELD_VALUE + trimed);
//				}

				dfm.put(df.getName(), trimed);
			}

		} catch (IOException e) {
			throw new DataMappingException(ERROR_DATA_READ_EXCEPTION + " " + e.getMessage());
		}

		return dfm;
	}
}