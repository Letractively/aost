var CommandList  = {
    rowCount : 0,
    recordIndex : 0,
    testCommands : null,

    rowInserted: function() {
        this.treebox.rowCountChanged(this.rowCount, 1);
        this.rowCount++;
        this.treebox.ensureRowIsVisible(this.recordIndex);
        this.recordIndex++;
    },

    getRecordIndex: function() {
        return this.recordIndex;
    },

    setTestCommands : function(elements){
        this.testCommands = elements;
    },

    deleteRow : function(index){
        this.treebox.rowCountChanged(index, -1);
        this.rowCount--;
        this.recordIndex--;
    },

    clearAll : function(){
        this.treebox.rowCountChanged(this.rowCount, -this.rowCount);
        this.rowCount = 0;
        this.recordIndex = 0;
    },

    getCellText : function(row, aColumn){
        var column = (aColumn.id) ? aColumn.id : aColumn; //Firefox pre1.5 compatibility
        var command = this.testCommands[row];

        if(column == "listCommandName" ){
            return command.name;
        }

        if(column == "listCommandRef"){
            return command.target;
        }

        if(column == "listCommandValue"){
            if(command.param == null || command.param == undefined){
                return "";
            }else{
                return command.param;
            }
        }

        return "todo.."
    },
    setTree: function(treebox){
        this.treebox = treebox;
    },
    isContainer: function(row){
        return false;
    },
    isSeparator: function(row){
        return false;
    },
    isSorted: function(){
        return false;
    },
    getLevel: function(row){
        return 0;
    },
    getImageSrc: function(row,col){
        return null;
    },
    getRowProperties: function(row,props){

    },
    getCellProperties: function(row,col,props){

    },
    getColumnProperties: function(colid,col,props){

    },

    cycleHeader: function(colid,elt){

    },
    cycleCell: function(rowid,colid){

    }
};