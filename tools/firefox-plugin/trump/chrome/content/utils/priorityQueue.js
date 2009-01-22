function PriorityQueue() {
   this.priorityArray = [];
    //{node : node, priority : common prefix length}
}

    // "public"
PriorityQueue.prototype.insert = function (node, priority) {
    var i = 0;
    while (i <= this.priorityArray.length && priority < (this.priorityArray[i]).priority ) {
        i++;
    }
    this.priorityArray.splice(i, 0, {"node": node, "priority": priority});
    return true;
}

PriorityQueue.prototype.get = function () {
    return this.priorityArray.shift() ;
}

PriorityQueue.prototype.peek = function () {
    return this.priorityArray[0] ;
}
