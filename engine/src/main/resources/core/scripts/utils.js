//Util file to hold commonly used data structures

//FIFo Queue

function FifoQueue(){
    this.queue = new Array();
};

FifoQueue.prototype.size = function(){
    return this.queue.length;
};

FifoQueue.prototype.peek = function(){
    if(this.queue.length > 0){
        return this.queue[0];
    }

    return null;
};

FifoQueue.prototype.push = function(obj){
    this.queue.push(obj);
};

FifoQueue.prototype.pop = function(){
    if(this.queue.length > 0){
        return this.queue.shift();
    }

    return null;
};

function HashMap()
{
    // members
    this.keyArray = new Array(); // Keys
    this.valArray = new Array(); // Values
};

//clone a copy of the Hash Map
HashMap.prototype.clone = function(){
    var newmap = new HashMap();
    if(this.keyArray.length > 0){
        newmap.keyArray = this.keyArray.slice(0);
    }
    if(this.valArray.length > 0){
        newmap.valArray = this.valArray.slice(0);
    }

    return newmap;
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

function SimpleCompare(){

};

SimpleCompare.prototype.compare = function(a, b){
    return a > b;    
};

function PriorityQueue(){  

    this.A = new Array();
    //you define your own comparator and overwrite this for our data
    this.comparator = new SimpleCompare();
};

PriorityQueue.prototype.size = function(){
    return this.A.length;
};

PriorityQueue.prototype.parent = function(val){
    return Math.floor((val-1)/2);
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
    if(l < this.A.length && this.comparator.compare(this.A[l], this.A[index])){
        largest = l;
    }else{
        largest = index;
    }

    if(r < this.A.length && this.comparator.compare(this.A[r], this.A[largest])){    
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
    while(i > 0 && this.comparator.compare(elem, this.A[this.parent(i)])){
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


//since Javascript associate array is actually an object with key as attribute
//and no length information, need to manually trace the size
function Hashtable(){
    this.map = {};
    this.length = 0;
};

Hashtable.prototype.exist = function(key){
    return typeof this.map[key] != "undefined";
};

Hashtable.prototype.put = function(key, val){
    if(!this.exist(key)){
        this.length++;
    }
    this.map[key] = val;
};

Hashtable.prototype.get = function(key){
    if(this.exist(key)){
        return this.map[key];
    }

    return null;
};

Hashtable.prototype.remove = function(key){
    if(this.exist(key)){
        this.length--;
        delete this.map[key];
    }
};

Hashtable.prototype.clear = function(){
    this.map = {};
    this.length = 0;
};

Hashtable.prototype.size = function(){
    return this.length;
};

Hashtable.prototype.keySet = function(){
    var keys = new Array();
    for(var key in this.map){
        keys.push(key);
    }

    return keys;
};

Hashtable.prototype.valSet = function(){
    var vals = new Array();
    for(var key in this.map){
        vals.push(this.map[key]);
    }

    return vals;
};

//clone a copy of the Hash Table
Hashtable.prototype.clone = function(){
    var newmap = new Hashtable();
    for(var key in this.map){
        newmap.put(key, this.map[key]);
    }

    return newmap;
};

Hashtable.prototype.showMe = function(){
    var sb = "";
    for(var key in this.map){
        sb = sb + " [" + key + "]=" + this.map[key];
    }

    return sb;
};

String.prototype.trim = function(){
    return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};

//String.prototype.startsWith = function(str){
//    return (this.match("^"+str)==str);
//};

//String.prototype.endsWith = function(str){
//    return (this.match(str+"$")==str);
//};

function objectExtends(destination, source1, source2) {
  for (var property in source1) {
    destination[property] = source1[property];
  }
  for (var property in source2) {
    destination[property] = source2[property];
  }

  return destination;
};

function objectCopy(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
};

function getObjectClass(obj) {
    if (obj && obj.constructor && obj.constructor.toString) {
        var arr = obj.constructor.toString().match(
                /function\s*(\w+)/);

        if (arr && arr.length == 2) {
            return arr[1];
        }
    }

    return undefined;
};


String.prototype.startsWith = function(str)
{
    return (this.indexOf(str) === 0);
};
//Have problem if the str starts with "*"
//String.prototype.startsWith = function(str)
//{return (this.match("^"+str)==str);}


String.prototype.beginsWith = function(t, i) {
    if (i==false) {
        return (t == this.substring(0, t.length)); 
    } else {
        return (t.toLowerCase() == this.substring(0, t.length).toLowerCase());
    }
};

String.prototype.endsWith = function(t, i) {
    if (i==false) {
        return (t== this.substring(this.length - t.length));
    } else {
        return (t.toLowerCase() == this.substring(this.length - t.length).toLowerCase());
    }
};