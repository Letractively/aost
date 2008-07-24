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

	private String id

	private String description

	private LinkedList<Field> fields = new LinkedList<Field>()

    public void addField(Field df){
        fields.addLast(df)
    }

    public LinkedList<Field> getFields() {
		return fields;
	}

    public void setFields(LinkedList<Field> fields) {
		this.fields = fields;
	}

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}