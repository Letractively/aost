

var TE = {
    /*
     * Mechanism for creating namespace modules. 
     * @param the string to convert to a namespace
     * */
    namespace:function(ns){
        var spaces = ns.split(".");
        var space=null;
        this.each(spaces,function(item){
            if(space===null && !this.isNull(eval(item))){
               space = eval(item); 
            }
            else if(this.isNull(space[item])){
               space[item] = {};
               space = space[item];
            }
            else space = space[item];
        },this);
        return space;
    },
    /* Shortcut for the namespace method
     * @param the string to convert to a namespace
     **/
    ns:function(ns){
        return this.namespace(ns);
    },
    /*
     * This functions iterate over an array
     * @param array The array to iterate
     * @param func The function to execute when iterating. Return false to
     * break the loop
     * @param scope The scope of the function, can be null
     * @return null or false if loop is broken;
     * */
    each:function(array,func,scope){
        if(this.isArray(array)!==true)return null;
        scope = scope||this;
        var ret = null;
        for(var i=0,l=array.length;i<l;i++){
            ret = func.call(scope,array[i],i); 
            if(ret===false)break;
        }
        return ret;
    },
    /*
     * This iterates over an array then returns an array of results returned by
     * @param array The array to iterate
     * @param func The function to execute when iterating. Return false to
     * break the loop
     * @param scope The scope of the function, can be null
     * @return {Array}
     * */
    map:function(array,func,scope){
        if(this.isArray(array)!==true)return [];
        var ret = [],temp;
        for(var i=0,l=array.length;i<l;i++){
            temp = func.call(scope,array[i],i); 
            if(!this.isNull(temp)) ret.push(ret);
        }
        return ret; 
    },
    /*
     * Checks if a parameter is an array
     * @param v the object to assert
     * @return {Boolean}
     * */
    isArray:function(v){
        return v instanceof Array;
    },
    /*
     * Checks if a parameters is either a null or undefined
     * @param v the object to assert
     * @return {Boolean}
     * */
    isNull:function(v){
        return v === null || v==undefined; 
    },
    /*
     * Checks if a parameters is either a null or undefined
     * @param v the object to assert
     * @return {Boolean}
     * */
    isString:function(v){
        return typeof v === "string";
    },
    merge:function(hash,hash2){
       for(var i in hash2){
           if(hash2.hasOwnProperty(i)){
               hash[i] = hash[i]||hash2[i];
           }
       }
    },
    util:{
        /*
         * Converts an iterable object to an array, copied from Prototype.js
         * */
        toArray:function(iterable){
            if (!iterable) return [];
            if (!(typeof iterable == 'function' && iterable == '[object NodeList]') &&
              iterable.toArray) {
              return iterable.toArray();
            } else {
              var results = [];
              for (var i = 0, length = iterable.length; i < length; i++)
                results.push(iterable[i]);
              return results;
            }
        }
    }
};

if (!Function.prototype.bind) {
    Function.prototype.bind = function() {
        var __method = this, args = TE.util.toArray(arguments),object = args.shift();
        return function() {
          return __method.apply(object, args.concat(TE.util.toArray(arguments)));
        };
    };	
}
if (!Function.prototype.inherits) {
    Function.prototype.inherits = function(parentCtor) {
        function tempCtor() {}
        tempCtor.prototype = parentCtor.prototype;
        this.superClass_ = parentCtor.prototype;
        this.prototype = new tempCtor();
        this.prototype.constructor = this;
    };
}
