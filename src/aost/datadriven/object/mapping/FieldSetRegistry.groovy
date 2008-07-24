package aost.datadriven.object.mapping
/**
 *
 * The Registry to hold all FieldSet defined
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class FieldSetRegistry {

    private Map<String, FieldSet> fieldSets = new HashMap<String, FieldSet>()

    public void addFieldSet(FieldSet fs){
        if(fs != null)
            fieldSets.put(fs.getId(), fs)
    }

    public FieldSet getFieldSet(String fieldSetId){
        return fieldSets.get(fieldSetId)
    }
}