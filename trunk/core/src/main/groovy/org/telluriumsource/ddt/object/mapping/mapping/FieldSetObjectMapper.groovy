package org.telluriumsource.ddt.object.mapping.mapping

import org.telluriumsource.ddt.object.mapping.FieldSetType

/**
 *  Interface for the field set object mapping
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
interface FieldSetObjectMapper {

    public FieldSetMapResult mapFieldSet(List data)
    public FieldSetType checkFieldSetType(List data)
    public List readNextLine(BufferedReader inputReader)
 
}