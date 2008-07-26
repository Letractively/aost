package aost.datadriven.object.mapping
/**
 *
 * The field set includes multiple fields and it usually is a record
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Jul 23, 2008
 *
 */
class FieldSet {

	private String name
    
	private String description

    private boolean hasIdentifier
    
    private LinkedList<Field> fields = new LinkedList<Field>()

    public int getFieldSize(){
        return fields.size()
    }
    
    public void addField(Field df){
        fields.addLast(df)
    }

    public LinkedList<Field> getFields() {
		return fields;
	}

    public void setFields(LinkedList<Field> fields) {
		this.fields = fields;
	}

    public boolean isHasIdentifier(){
        return this.hasIdentifier
    }

    public void setHasIdentifier(boolean identifier){
        this.hasIdentifier = identifier    
    }

    public IdentifierField getIdentifierField(){
        
        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof IdentifierField){
                    return f
                }
            }
        }
        
        return null
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public boolean checkIfIdentifierPresented(){
        boolean result = false

        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof IdentifierField){
                    result = true
                }
            }
        }

        this.hasIdentifier = result

        return result
    }
}