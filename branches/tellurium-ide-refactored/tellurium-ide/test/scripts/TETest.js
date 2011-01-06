function TETest(name) {
    TestCase.call(this,name);
}
 
TETest.inherits(TestCase); 

TETest.prototype.testNamespace=function() {
    this.assertNotNull("TE namespace is not null",TE);
    TE.ns("TE.main");
    this.assertNotNull("TE.main namespace is created",TE.main);
    ////check if existing namespace is not overwritten
    TE.main.dummy = "test";
    TE.ns("TE.main.newns");
    this.assertEquals("TE.main.dummy is still available","test",TE.main.dummy);
    this.assertNotNull("TE.main.newns exists",TE.main.newns);
};

TETest.prototype.testMap=function() {
    //map will return an array
    var array = TE.map([1,2,3],function(item){
        return item.toString(); 
    },this);
    this.assertEquals("Returned by map should be the same",3,array.length);

    //if function returns a null, it wont be appended to the returned array
    array = TE.map([1,2,3],function(item){
        return null; 
    },this);
    this.assertEquals("Returned by map size should be 0",0,array.length);
};

TETest.prototype.testMerge=function(){
    var testFunction = function(){
        
    }
    //test if merge function extends the prototype
    TE.merge(testFunction.prototype,{
        property1:1,
        func1:function(){
            return "TEST";
        },
        func2:function(){
            return this.property1; 
        }
    })
    var clazz = new testFunction(); 
    this.assertEquals("testFunction has func1","TEST" , clazz.func1());
    this.assertEquals("testFunction has property1",1 , clazz.property1);
    this.assertEquals("testFunction func2 can access property1",1 , clazz.func2());
}
