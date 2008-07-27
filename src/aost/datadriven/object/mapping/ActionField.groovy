package aost.datadriven.object.mapping
/**
 * Action field and it identified which method the field set should should apply to 
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 27, 2008
 * 
 */
class ActionField extends Field{

    //the action, i.e., method name
    private String value

    public void setValue(String value){
        this.value = value
    }

    public String getValue(){
        return this.value
    }
}