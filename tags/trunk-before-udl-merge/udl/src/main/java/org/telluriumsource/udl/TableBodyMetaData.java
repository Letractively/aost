package org.telluriumsource.udl;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Feb 18, 2010
 */
public class TableBodyMetaData extends MetaData {
    public TableBodyMetaData(){

    }
    
    public TableBodyMetaData(String id) {
        super(id);
    }

    protected Index tbody;

    protected Index row;

    protected Index column;

    public Index getTbody() {
        return tbody;
    }

    public void setTbody(Index tbody) {
        this.tbody = tbody;
    }

    public Index getRow() {
        return row;
    }

    public void setRow(Index row) {
        this.row = row;
    }

    public Index getColumn() {
        return column;
    }

    public void setColumn(Index column) {
        this.column = column;
    }
}
