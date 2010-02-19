package org.telluriumsource.udl;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class ListMetaData extends MetaData {

    public ListMetaData(String id, Index index) {
        super(id);
        this.index = index;
    }

    public ListMetaData(String id, String index) {
        super(id);
        this.index = new Index(index);
    }

    protected Index index;

    public Index getIndex() {
        return index;
    }

    public void setIndex(Index index) {
        this.index = index;
    }
}
