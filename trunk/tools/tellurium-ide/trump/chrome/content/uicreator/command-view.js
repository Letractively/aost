var CommandView  = {
    init: function() {
        this.rowCount = 0;
        this.recordIndex = 0;
        this.testCommands = null;
    },

    cmdStart: function(cmd) {
        var index = this.getIndex(cmd);
        if(index != -1){
            this.testCommands[index].status = TestState.RUNNING;
            this.treebox.invalidateRow(index); 
        }
    },

    cmdSucceed: function(cmd) {
        var index = this.getIndex(cmd);
        if(index != -1){
            this.testCommands[index].status = TestState.SUCCEED;
            this.treebox.invalidateRow(index);
        }
    },

    cmdFailed: function(cmd) {
        var index = this.getIndex(cmd);
        if(index != -1){
            this.testCommands[index].status = TestState.FAILED;
            this.treebox.invalidateRow(index);
        }
    },

    clear: function(){
        if(this.testCommands != null && this.testCommands.length > 0){
            for(var i=0; i<this.testCommands.length; i++){
                this.testCommands[i].status = TestState.READY;
            }
//            this.treebox.invalidateColumn(4);
            this.treebox.invalidate();
        }
    },

    getIndex: function(cmd) {
        if (this.testCommands != null && this.testCommands.length > 0) {
            for (var i = 0; i < this.testCommands.length; i++) {
                if (this.testCommands[i].seq == cmd.seq)
                    return i;
            }
        }

        return -1;
    },

    rowInserted: function() {
        this.treebox.rowCountChanged(this.rowCount, 1);
        this.rowCount++;
        this.treebox.ensureRowIsVisible(this.recordIndex);
        this.recordIndex++;
    },

    insertCommand: function(index, cmd){
        this.testCommands.splice(index, 0, cmd);
        this.treebox.rowCountChanged(this.rowCount, 1);
        this.rowCount++;
        this.treebox.ensureRowIsVisible(index);
        this.recordIndex++;        
    },

    updateCommands: function(refUidMap){
        for(var i=0; i<this.testCommands.length; i++){
            var cmd = this.testCommands[i];
            if(cmd.ref != null){
                var uid = refUidMap.get(cmd.ref);
                if(uid != null){
                    cmd.target = uid;
                    this.treebox.invalidateRow(i); 
                }
            }
        }
    },

    getRecordByIndex: function(index){
        if(index >= 0 && index < this.testCommands.length){
            return this.testCommands[index];
        }

        return null;
    },

    getRecordIndex: function() {
        return this.recordIndex;
    },

    setTestCommands : function(elements){
        this.testCommands = elements;
        this.treebox.rowCountChanged(this.rowCount, + this.testCommands.length);
        this.rowCount = this.rowCount + this.testCommands.length;
        this.recordIndex = this.recordIndex + this.testCommands.length;
        this.treebox.ensureRowIsVisible(this.recordIndex);
    },
    
    deleteFromTestCommand : function(cmd) {
    	var index = this.getIndex(cmd);
    	if (this.testCommands != null && this.testCommands.length > 0 && index < this.testCommands.length) {
            this.testCommands.splice(index , 1);
            this.deleteRow(index);
    	}	
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
        this.testCommands = null;
        this.treebox.invalidate();
    },

    getCellText : function(row, aColumn){
        var column = (aColumn.id) ? aColumn.id : aColumn; //Firefox pre1.5 compatibility
        var command = this.testCommands[row];

        if(column == "recordedCommandName" ){
            return command.name;
        }

        if(column == "recordedCommandUid"){
            return command.target;
        }

        if(column == "recordedCommandValue"){
            if(command.value == undefined || command.value == null){
                return "";
            }else{
                return command.value;
            }
        }

        if(column == "recordedCommandVariable"){
            if(command.returnVariable == undefined || command.returnVariable == null){
                return "";
            }else{
                return command.returnVariable;
            }
        }

        if(column == "recordedCommandResult"){
            return command.status;
        }

        throw Error("Invalid pass in value: row= " + row + ", aColumn=" + aColumn + ", column=" + column + ", command=" + command.name);
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