
function Recorder(window) {
//    alert("Recorder")
    this.window = window;
    this.parentWindow = this.window.opener;
    this.builder = new Builder();
    this.decorator = new Decorator();
    this.listener = null;
    this.selectedElements = new Array();
    this.tagObjectArray = new Array();
    this.recordListBox = document.getElementById("recordListBox");
}

Recorder.WINDOW_RECORDER_PROPERTY = "_TrUMP_IDE_Recorder";

Recorder.prototype.registerListener = function(){
    this.registerClickListener();
}

Recorder.prototype.unregisterListener = function(){
    this.unregisterClickListener();
}

Recorder.prototype.registerClickListener = function(){
    var self = this;
    this.listener =
        function(event){
            event.preventDefault();
            var element = event.target;
            //check if the element is already selected
            var index = self.selectedElements.indexOf(element);
            if(index == -1){
                self.decorator.addBackground(element);
                self.selectedElements.push(element);

                var tagObject = self.builder.createTagObject(element);
                var name = getAttributeNameOrId(element)

                var cellOne = createListCell(tagObject.tag);
                var cellTwo = createListCell(name);
                var listItem = document.createElement("listitem");
                listItem.appendChild(cellOne);
                listItem.appendChild(cellTwo);

                self.tagObjectArray.push(tagObject);
                self.recordListBox.appendItem(listItem);
            }else{
                //we are assuming to remove the element
                self.decorator.removeBackground(element);
                self.selectedElements.splice(index, 1);
                self.tagObjectArray.splice(index, 1);
                self.recordListBox.removeItemAt(index);
            }

        };
    this.parentWindow.document.addEventListener("click", this.listener, false);
}

Recorder.prototype.unregisterClickListener = function(){
//    alert("unregisterClickListener()")
    for(var i=0; i< this.selectedElements.length ; ++i){
        alert(this.tagObjectArray[i]);
        this.decorator.removeBackground(this.selectedElements[i]);
    }
    this.parentWindow.document.removeEventListener("click", this.listener, false);
    this.listener = null;
}

Recorder.prototype.stopRecording = function(){
    
}



