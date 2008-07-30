package org.tellurium.ddt.object.mapping
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

    private boolean hasAction
    
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

    public boolean isHasAction(){
        return this.hasAction
    }

    public void setHasAction(boolean hasAction){
        this.hasAction = hasAction    
    }

    public boolean isHasIdentifier(){
        return this.hasIdentifier
    }

    public void setHasIdentifier(boolean hasIdentifier){
        this.hasIdentifier = hasIdentifier
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

    public ActionField getActionField(){
        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof ActionField){
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

    public void checkFields(){

        if(!fields.isEmpty()){
            for(Field f : fields){
                if(f instanceof IdentifierField){
                    this.hasIdentifier = true
                }
                if(f instanceof ActionField){
                    this.hasAction = true
                }
            }
        }
    }
}