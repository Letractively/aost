package org.tellurium.ddt.object.mapping

import org.tellurium.ddt.object.mapping.builder.FieldBuilder
import org.tellurium.ddt.object.mapping.builder.FieldSetBuilder
import org.tellurium.ddt.object.mapping.builder.IdentifierFieldBuilder
import org.tellurium.ddt.object.mapping.builder.ActionFieldBuilder

/**
 * parse the Field Set definition
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldSetParser extends BuilderSupport{
    protected final static String FIELD_SET = "FieldSet"
    protected final static String FIELD = "Field"
    protected final static String IDENTIFIER = "Identifier"
    protected final static String ACTION = "Action"

    private FieldSetRegistry registry

    public FieldSetParser(FieldSetRegistry registry){
        this.registry = registry
    }
    
    private FieldBuilder fb = new FieldBuilder()
    private FieldSetBuilder fsb = new FieldSetBuilder()
    private IdentifierFieldBuilder fsi = new IdentifierFieldBuilder()
    private ActionFieldBuilder afb = new ActionFieldBuilder()

    protected void setParent(Object parent, Object child) {
        if (parent instanceof FieldSet) {
            FieldSet fs = (FieldSet)parent
            fs.addField(child)
        }
    }

    protected Object createNode(Object name) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return new FieldSet()
        if(FIELD.equalsIgnoreCase(name))
            return new Field()
        if(IDENTIFIER.equalsIgnoreCase(name))
            return new IdentifierField()
        if(ACTION.equalsIgnoreCase(name))
            return new ActionField()

        return null
    }

    protected Object createNode(Object name, Object value) {
        return null  
    }

    protected Object createNode(Object name, Map map) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map)
        if(FIELD.equalsIgnoreCase(name))
            return fb.build(map)
        if(IDENTIFIER.equalsIgnoreCase(name))
            return fsi.build(map)
        if(ACTION.equalsIgnoreCase(name))
            return afb.build(map)

        return null
    }

    protected Object createNode(Object name, Map map, Object value) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map, (Closure)value)

        return null
    }

    protected void nodeCompleted(Object parent, Object node) {
        //when the node is completed and it is a FieldSet, put it into the registry
        if (node instanceof FieldSet) {
            
            FieldSet fs = (FieldSet)node

            //need to check if the identifier is presented
            fs.checkFields()

            //only put the top level nodes into the registry
            registry.addFieldSet(fs)
        }

    }

}