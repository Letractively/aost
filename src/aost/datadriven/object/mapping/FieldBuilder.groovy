package aost.datadriven.object.mapping
/**
 * Build Field from a collection of attributes
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 24, 2008
 *
 */
class FieldBuilder extends BaseBuilder{
    protected static final String NAME = "name"
    protected static final String TYPE = "type"
    protected static final String NULLABLE = "nullable"
    protected static final String NULLVALUE = "nullValue"
    protected static final String LENGTH = "length"
    protected static final String PATTERN = "pattern"

    public build(Map map) {
        map = makeCaseInsensitive(map)

        Field f = new Field()
        f.name = map.get(NAME)
        f.description = map.get(DESCRIPTION)

        //do not override the default type
        if(map.get(TYPE) != null)
            f.type = map.get(TYPE)

        if(map.get(NULLABLE) != null)
            f.nullable = map.get(NULLABLE)

        f.nullValue = map.get(NULLVALUE)

        if(map.get(LENGTH) != null)
            f.length = map.get(LENGTH)

        f.pattern = map.get(PATTERN)
        
        return f

    }

}