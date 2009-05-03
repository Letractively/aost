//Util file to hold commonly used data structures

function HashMap()
{
    // members
    this.keyArray = new Array(); // Keys
    this.valArray = new Array(); // Values
};

HashMap.prototype.put = function(key, val){
    var elementIndex = this.findIt( key );

    if( elementIndex == (-1) )
    {
        this.keyArray.push( key );
        this.valArray.push( val );
    }
    else
    {
        this.valArray[ elementIndex ] = val;
    }
};

HashMap.prototype.get = function( key ){
    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != (-1) )
    {
        result = this.valArray[ elementIndex ];
    }

    return result;
};

 HashMap.prototype.removeAt = function( index )
{
  var part1 = this.slice( 0, index);
  var part2 = this.slice( index+1 );

  return( part1.concat( part2 ) );
};

 HashMap.prototype.removeArrayAt = function(ar, index )
{
  var part1 = ar.slice( 0, index);
  var part2 = ar.slice( index+1 );

  return( part1.concat( part2 ) );
};

HashMap.prototype.remove = function ( key )
{
//    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != -1 )
    {
        this.keyArray = this.removeArrayAt(this.keyArray, elementIndex);
        this.valArray = this.removeArrayAt(this.valArray, elementIndex);
//        this.keyArray = this.keyArray.removeAt(elementIndex);
//        this.valArray = this.valArray.removeAt(elementIndex);
    }

//    return ;
};

HashMap.prototype.size = function()
{
    return (this.keyArray.length);
};

HashMap.prototype.clear = function()
{
    for( var i = 0; i < this.keyArray.length; i++ )
    {
        this.keyArray.pop(); this.valArray.pop();
    }
};

HashMap.prototype.keySet = function()
{
    return (this.keyArray);
};

HashMap.prototype.valSet = function()
{
    return (this.valArray);
};

HashMap.prototype.showMe = function()
{
    var result = "";

    for( var i = 0; i < this.keyArray.length; i++ )
    {
        result += "Key: " + this.keyArray[ i ] + "\tValues: " + this.valArray[ i ] + "\n";
    }
    return result;
};

HashMap.prototype.findIt = function( key )
{
    var result = (-1);

    for( var i = 0; i < this.keyArray.length; i++ )
    {
        if( this.keyArray[ i ] == key )
        {
            result = i;
            break;
        }
    }
    return result;
};

function StringBuffer() {
    this.buffer = [];
};

StringBuffer.prototype.append = function(string) {
    this.buffer.push(string);
    return this;
};

StringBuffer.prototype.toString = function() {
    return this.buffer.join("");
};

function trimString(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
};

function PriorityQueue(){  
    //assume the added data should include a key field
    this.A = new Array();
};

PriorityQueue.prototype.size = function(){
    return this.A.length;
};

PriorityQueue.prototype.parent = function(val){
    return Math.floor((val-1)/2)
};

PriorityQueue.prototype.left = function(val){
    return 2*val+1;
};

PriorityQueue.prototype.right = function(val){
    return 2*val+2;
};

PriorityQueue.prototype.heapify = function(index){
    var l = this.left(index);
    var r = this.right(index);

    var largest;
    if(l < this.A.length && this.A[l].key > this.A[index].key){
        largest = l;
    }else{
        largest = index;
    }

    if(r < this.A.length && this.A[r].key > this.A[largest].key){
        largest = r;
    }

    if(largest != index){
        //exchange A[index] and A[largest]
        var tmp = this.A[index];
        this.A[index] = this.A[largest];
        this.A[largest] = tmp;
        this.heapify(largest);
    }
};

PriorityQueue.prototype.insert = function(elem){
    this.A.push(elem);
    var i = this.A.length-1;
    while(i > 0 && this.A[this.parent(i)].key < elem.key){
        this.A[i] = this.A[this.parent(i)];
        i = this.parent(i);
    }
    this.A[i] = elem;
};

PriorityQueue.prototype.extractMax = function(){
    if(this.A.length < 1)
        return null;

    var max = this.A[0];
    var last = this.A.pop();
    if(this.A.length > 0){
        this.A[0] = last;
        this.heapify(0);
    }
    
    return max;
};

PriorityQueue.prototype.buildHeap = function(){
    for(var i=Math.floor(this.A.length/2)-1; i>=0; i--){
        this.heapify(i);
    }
};
