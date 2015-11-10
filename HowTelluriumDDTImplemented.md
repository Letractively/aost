# Introduction #

[Data Driven Testing](http://code.google.com/p/aost/wiki/DataDrivenTesting) is one of the most important features of Tellurium. You can define data format in an expressive way. In you data file, you can specify which test you want to run, the input parameters, and expected results. Tellurium automatically binds the input data to variables defined in your test script and run the tests you specified in the input file. The test results will be recorded by a test listener and output in different formats, for example, an XML file.

The system diagram is shown in Figure 1.

http://tellurium-users.googlegroups.com/web/TelluriumDataDrivenSmall.jpg?gda=WePTVk4AAAD5mhXrH3CK0rVx4StVj0LYYQ-a0sZzxEmmZWlHbP2MWzTrErMgjh_s8-a7FfMpsGsNL5bu5vgQy2xsA01CuO9M47Cl1bPl-23V2XOW7kn5sQ&gsc=ODykzgsAAACEc2FtJPGdXe_7CHb1VB6Z

Figure 1. System Diagram for Tellurium Data Driven Testing.

You may wonder how the data driven testing is implemented in Tellurium. This article will introduce some implementation details about it.

# Implementations #

## the Field Set Object Mapping Framework ##

Historically, the Tellurium Data Driven evolved from [the Field Set Object Mapping Framework (FSOM)](http://code.google.com/p/jianwikis/wiki/FieldSetObjectMappingFramework) I worked for a batch processor. The FSOM framework acts as a converter layer, i.e., to convert Java Objects to and from field sets. What are field sets? A field set is a set of data to represent the format of a flat file or other types of files.

The Architecture of the FSOM framework is shown in the following diagram,

http://sacct-users.googlegroups.com/web/FsomArchitecture.png?gda=myxegUcAAABwsiXcufpgHYK0Fq-t18-VcduyJajMZkHyExt4IRNTJkxBcav97KKqIIHPw8_lwXUVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=ypY6FgsAAADV3ZfoSfo7zlUr2Cv4XdfJ

Figure 2. The Architecture of the FSOM framework.

As you can see from the above architecture, Tellurium inherited a lot of concepts from the FSOM framework.

## Tellurium Data Driven Testing ##

The major move from the FSOM framework to Tellurium Data Driven Testing is the adoption of the Groovy BuilderSupport class, which removed the need for the Spring bean factory and XML configuration parser. The Groovy BuilderSupport class makes it very easy to write the Field Set in an expressive way. For example,


```
//define custom data type and its type handler

typeHandler "phoneNumber", "org.tellurium.test.PhoneNumberTypeHandler"

//define file data format
fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search"){
    Field(name: "regularSearch", type: "boolean", 
          description: "whether we should use regular search or use I'm feeling lucky")
    Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
    Field(name: "input", description: "input variable")
}
```

Note that the above script defines a custom type "PhoneNumber" and Tellurium automatically calls this type handler to convert the input data to the "PhoneNumber" Java type.

Under the hood, Tellurium uses the Groovy BuilderSupport class to parse the nested objects and here is the code for that purpose.

```
class FieldSetParser extends BuilderSupport{
    protected final static String FIELD_SET = "FieldSet"
    protected final static String FIELD = "Field"
    protected final static String IDENTIFIER = "Identifier"
    protected final static String TEST = "Test"

    private FieldSetRegistry registry

    public FieldSetParser(FieldSetRegistry registry){
        this.registry = registry
    }
    
    private FieldBuilder fb = new FieldBuilder()
    private FieldSetBuilder fsb = new FieldSetBuilder()
    private IdentifierFieldBuilder fsi = new IdentifierFieldBuilder()
    private TestFieldBuilder afb = new TestFieldBuilder()

    protected void setParent(Object parent, Object child) {
        if (parent instanceof FieldSet) {
            FieldSet fs = (FieldSet)parent
            fs.addField(child)
        }
    }

    protected Object createNode(Object name) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return new FieldSet()
        if(FIELD.equalsIgnoreCase(name))
            return new Field()
        if(IDENTIFIER.equalsIgnoreCase(name))
            return new IdentifierField()
        if(TEST.equalsIgnoreCase(name))
            return new TestField()

        return null
    }

    protected Object createNode(Object name, Object value) {
        return null  
    }

    protected Object createNode(Object name, Map map) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map)
        if(FIELD.equalsIgnoreCase(name))
            return fb.build(map)
        if(IDENTIFIER.equalsIgnoreCase(name))
            return fsi.build(map)
        if(TEST.equalsIgnoreCase(name))
            return afb.build(map)

        return null
    }

    protected Object createNode(Object name, Map map, Object value) {
        if(FIELD_SET.equalsIgnoreCase(name))
            return fsb.build(map, (Closure)value)

        return null
    }

    protected void nodeCompleted(Object parent, Object node) {
        //when the node is completed and it is a FieldSet, put it into the registry
        if (node instanceof FieldSet) {
            
            FieldSet fs = (FieldSet)node

            //need to check if the identifier is presented
            fs.checkFields()

            //only put the top level nodes into the registry
            registry.addFieldSet(fs)
        }

    }

}
```

# Summary #

The Object to FieldSet mapping is only one aspect of the Tellurium Data Driven testing. For more detailed implementation of the Tellurium Data Driven mechanism, please read [Tellurium Data Driven Testing](http://code.google.com/p/aost/wiki/UserGuide070DetailsOnTellurium#Data_Driven_Testing) and Tellurium core source code with the package name `org.tellurium.ddt`.

# Resources #

  * [The Field Set Object Mapping Framework](http://code.google.com/p/jianwikis/wiki/FieldSetObjectMappingFramework)
  * [Tellurium Data Driven Testing](http://code.google.com/p/aost/wiki/UserGuide070DetailsOnTellurium#Data_Driven_Testing)