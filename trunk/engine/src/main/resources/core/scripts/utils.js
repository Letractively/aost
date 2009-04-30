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
    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != -1 )
    {
        this.keyArray = this.removeArrayAt(this.keyArray, elementIndex);
        this.valArray = this.removeArrayAt(this.valArray, elementIndex);
//        this.keyArray = this.keyArray.removeAt(elementIndex);
//        this.valArray = this.valArray.removeAt(elementIndex);
    }

    return ;
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

StringBuffer.prototype.append = function append(string) {
    this.buffer.push(string);
    return this;
};

StringBuffer.prototype.toString = function toString() {
    return this.buffer.join("");
};

function trimString(str) {
    return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
};

