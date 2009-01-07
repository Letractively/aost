

var TreeView = {

    rowCount : 10000,

    getCellText : function(row, column){
            var colId = column.id != null ? column.id : column;
            var command = this.getCommand(row);
            if (command.type == 'command') {
                return command[colId];
            } else if (command.type == 'comment') {
                return colId == 'command' ? command.comment : '';
            } else {
                return null;
            }
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

    }
};