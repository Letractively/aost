function Data(){
  this.key = null;
};

var B = [2, 8, 4, 14, 7, 1, 16, 9, 10, 3]

var queue = new PriorityQueue();

for(var i=0; i<10; i++){
   var data = new Data();
   data.key = B[i];
   queue.insert(data);
}

while(queue.size() > 0){
    var max = queue.extractMax();
}