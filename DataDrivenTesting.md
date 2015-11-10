

## Data Driven Testing ##

Data Driven Testing is a different way to write tests, i.e, separate test data from the test scripts and the test flow is not controlled by the test scripts. It is controlled by the input file instead. In the input file, users can specify which test to run, what are input parameters, and what are expected results.

Data driven testing is a new feature in Tellurium 0.4.0. The system diagram is shown in Figure 1.

http://tellurium-users.googlegroups.com/web/tellurium.data.driven.jpg?gda=_1pLcEsAAACjbvY1r0rMz6MRbvC-tSqoysMSW8LOE1CninSxbdL8I2ArWKkWH9uGyxs8H0RkPsAdq11J_EY5XIrmvbkBlJwJBkXa90K8pT5MNmkW1w_4BQ&gsc=httiOwsAAAAISLBF63vg48UkWer0gDjz

Figure 1. System Diagram for Tellurium Data Driven Testing

The tellurium data driven test consists of three main parts, i.e., Data Provider, TelluriumDataDrivenModule, and TelluriumDataDrivenTest.

### Data Provider ###

The data provider is responsible for reading data from input stream and converting data to Java variables in data driven tests. As shown in Figure 1, the data reader reads data from the input stream line by line and turns the results to the FieldSet-to-Object mapper, which maps the data to the appropriate FieldSet. Then, the varaiable binder binds the data to variables defined in the test according to the FieldSet defintion.

The data provider is the key component of the data driven testing. It reads data from input and binds them to variables defined in the test scripts. This part is handled by the data to object mapping framework. Data provider usually works behind the scene and it provides the following methods for users:

  1. loadData file\_name, load input data from a file
  1. useData String\_name, load input data from a String in the test script
  1. bind(field\_name), bind a variable to a field in a field set
  1. closeData, close the input data stream and report the test results
  1. cacheVariable(name, variable), put variable into cache
  1. getCachedVariable(name, variable), get variable from cache

The file name should include the file path, for example,

```
  loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
```

Right now, Tellurium supports pipe format input file and will add more file format support later on. To change the file reader for different formats, please change the following settings in the configuration file TelluriumConfig.groovy:

```
datadriven{
    dataprovider{
        //specify which data reader you like the data provider to use
        //the valid options include "PipeFileReader", "CVSFileReader" at this point
        reader = "PipeFileReader"
    }
}

```

Sometimes, you may like to specify test data in the test scripts directly, useData is designed for this purpose and it loads input from a String. The String is usually defined in Groovy style using triple quota, for example:

```
  protected String data = """
    google_search | true | 865-692-6000 | tellurium
    google_search | false| 865-123-4444 | tellurium selenium test
    google_search | true | 755-452-4444 | tellurium groovy
    google_search | false| 666-784-1233 | tellurium user group
    google_search | true | 865-123-5555 | tellurium data driven
"""
    ...

  useData data
```

bind is the command to bind a variable to input Field Set field at runtime. FieldSet is the format of a line of data and it is defined in the next section. For example,

```
  def row = bind("GCHLabel.row")
```

is used to bind the row variable to the "row" field in the FieldSet "GCHLabel". Tellurium does not explicitly differentiate input parameters from the expected results in the input data. You only need to bind variables to the input data and then you can use any of them as the expected results for result comparison.

cacheVariable and getCachedVariable are used to pass intermediate variables among tests. cacheVariable is used to put variable into a cache and getCachedVariable is used to get back the variable. For example,

```
int headernum = getTableHeaderNum()
cacheVariable("headernum", headernum)

...

int headernum = getCachedVariable("headernum")
...
```

When you are done with your testing, please use "closeData" to close the input data stream. In the meantime, the result reporter will output the test results in the format you specified in the configuration file, for example, XML file as shown in the TelluriumConfig.groovy file:

```
test{
    result{
       //specify what result reporter used for the test result
       //valid options include "SimpleResultReporter", "XMLResultReporter", and "StreamXMLResultReporter"
       reporter = "XMLResultReporter"
       //the output of the result
       //valid options include "Console", "File" at this point
       //if the option is "File", you need to specify the file name, other wise it will use the default
       //file name "TestResults.output"
       output = "Console"
       //test result output file name
       filename = "TestResult.output"
    }
}
```

### Tellurium Data Driven Module ###

TelluriumDataDrivenModule is used to define modules, where users can define Ui Modules, FieldSets, and tests as shown in Figure 2. Users should extend this class to define their own test modules.

http://tellurium-users.googlegroups.com/web/tellurium.ddt.sequence.module.jpg?gda=JfGNJFMAAACjbvY1r0rMz6MRbvC-tSqoysMSW8LOE1CninSxbdL8I2vnDwbH8gszdz4ehPdDgG9Ob8FqU5PL6F5asAt-s2HZMrYifh3RmGHD4v9PaZfDexVi73jmlo822J6Z5KZsXFo&gsc=httiOwsAAAAISLBF63vg48UkWer0gDjz

Figure 2. The Sequence Diagram of defineModule in TelluriumDataDrivenModule


TelluriumDataDrivenModule provides one method "defineModule" for users to implement. Since it extends the DslContext class, users can define Ui modules just like in regular Tellurium Ui Module. For example:

```
ui.Table(uid: "labels_table", clocator: [:], group: "true"){
   TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
   Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
        UrlLink(uid: "all", locator: "/a")
   }
}
```

FieldSet is used to define the format of one line of input data and FieldSet consists of fields, i.e., columns, in the input data. There is a special field "test", which users can specify what test this line of data apply to. For example,

```
fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
    Test(value: "getGCHStatus")
    Field(name: "label")
    Field(name: "rowNum", type: "int")
    Field(name: "columNum", type: "int")
}  
```

The above FieldSet defines the input data format for testing google code hosting web page. Note, the Test field must be the first column of the input data. The default name of the test field is "test" and does not need to be specified. If the value attribute of the test field is not specified, it implies this same format, i.e., FieldSet, can used for different tests.


For regular field, it includes the following attributes:

```

class Field {
	//Field name
	private String name

        //Field type, default is String
        private String type = "String"

        //optional description of the Field
	private String description

	//If the value can be null, default is true
	private boolean nullable = true

	//optional null value if the value is null or not specified
	private String nullValue

	//If the length is not specified, it is -1
	private int length = -1

	//optional String pattern for the value
	//if specified, we must use it for String validation
	private String pattern
   } 

```

Tellurium can automatically handle Java primitive types. Another flexibility Tellurium provides is that users can define their own custom type handlers to deal with more complicated data types by using "typeHandler", for example,

```
//define custom data type and its type handler
typeHandler "phoneNumber", "org.tellurium.test.PhoneNumberTypeHandler"

//define file data format
fs.FieldSet(name: "fs4googlesearch", description: "example field set for google search") {
    Field(name: "regularSearch", type: "boolean", description: "whether we should use regular search or use I'm feeling lucky")
    Field(name: "phoneNumber", type: "phoneNumber", description: "Phone number")
    Field(name: "input", description: "input variable")
}
```

The above script defined a custom type "PhoneNumber" and the Tellurium will automatically call this type handler to convert the input data to the "PhoneNumber" Java type.

The "defineTest" method is used to define a test in the TelluriumDataDrivenModule, for example, the following script defines the "clickGCHLabel" test:

```
defineTest("clickGCHLabel"){
    def row = bind("GCHLabel.row")
    def column = bind("GCHLabel.column")
           
    openUrl("http://code.google.com/hosting/")
    click  "labels_table[2][1].[${row}][${column}]"

    waitForPageToLoad 30000
}
```

Note, the bind command is used to bind variables row, column to the fields "row" and "column" in the FieldSet "GCHLabel".

Tellurium also provide the command "compareResult" for users to compare the actual result with the expected result. For example, the following script compares the expected label, row number, and column number with the acutal ones at runtime:

```
defineTest("getGCHStatus"){
    def expectedLabel = bind("GCHStatus.label")
    def expectedRowNum = bind("GCHStatus.rowNum")
    def expectedColumnNum = bind("GCHStatus.columNum")

    openUrl("http://code.google.com/hosting/")
    def label = getText("labels_table[1][1]")
    def rownum = getTableMaxRowNum("labels_table[2][1]")
    def columnum = getTableMaxColumnNum("labels_table[2][1]")

    compareResult(expectedLabel, label)
    compareResult(expectedRowNum, rownum)
    compareResult(expectedColumnNum, columnum)
    pause 1000
}
```

Sometimes, users may need custom "compareResult" to handle more complicated situation, for example, users want to compare two lists. In such a case, users can override the default "compareResult" behaviour by specifying custom code in the closure:

```
compareResult(list1, list2){
    assertTrue(list1.size() == list2.size())
    for(int i=0; i<list1.size();i++){
        //put your custom comparison code here
    }
}
```

If users want to check a variable in the test and the "checkResult" method should be used, which comes with a closure where users can define the actually assertions inside.

```
checkResult(issueTypeLabel) {
    assertTrue(issueTypeLabel != null)
}
```

Like "compareResult", "checkResult" will capture all assertion errors. The test will resume even the assertions fail and the result will be reported in the output.

In addition to that the "logMessage" can be used for users to log any messages in the output.

```
logMessage "Found ${actual.size()} ${issueTypeLabel} for owner " + issueOwner 
```

### Tellurium Data Driven Test ###

TelluriumDataDrivenTest is the class users should extend to run the actual data driven testing and it is more like a data driven testing engine. There is only one method, "testDataDriven", which users need to implement. The testing process is shown in Figure 3.


http://tellurium-users.googlegroups.com/web/ddt_test_sequence.jpg?gda=M_GJLEcAAACjbvY1r0rMz6MRbvC-tSqoOiEqD7Mi6G5cLzJ9CIeLB-TPT63Q-1cxaIO0tQmR15XiNflBnS90ecEO3zvz3dEqeV4duv6pDMGhhhZdjQlNAw&gsc=httiOwsAAAAISLBF63vg48UkWer0gDjz

Figure 3. The Sequence Diagram of testDataDriven in TelluriumDataDrivenTest

Usually, users need to do the following steps:

  1. use "includeModule" to load defined Modules
  1. use "loadData" or "useData" to load input data stream
  1. use "stepToEnd" to read the input data line by line and pick up the specified test and run it, until reaches the end of the data stream
  1. use "closeData" to close the data stream and output the test results

What the "includeModule" does is to merge in all Ui modules, FieldSets, and tests defined in that module file to the global registry. "stepToEnd" will look at each input line, first to find the test name and pass in all input parameters to it, and then run the test. The whole process can be illustrated in the following example:

```
class GoogleBookListCodeHostTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {

        includeModule  example.google.GoogleBookListModule.class
        includeModule  example.google.GoogleCodeHostingModule.class

        //load file
        loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
        
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()
    }
}

```

The input data for this example are as follows,

```
##TEST should be always be the first column

##Data for test "checkBookList"
##TEST | CATEGORY | SIZE
checkBookList|Fiction|8
checkBookList|Fiction|3

##Data for test "getGCHStatus"
##TEST | LABEL | Row Number | Column Number
getGCHStatus |Example project labels:| 3 | 6
getGCHStatus |Example project| 3 | 6

##Data for test "clickGCHLabel"
##TEST | row | column
clickGCHLabel | 1 | 1
clickGCHLabel | 2 | 2
clickGCHLabel | 3 | 3
```

Note that line starting with "##" is comment line and empty line will be ignored.

If some times, users want to control the testing execution flow by themselves, Tellurium also provides this capability even though it is not recommended. Tellurium provides two additional commands, i.e., "step" and "stepOver". "step" is used to read one line of input data and run it, and "stepOver" is used to skip one line of input data. In this meanwhile, Tellurium also allows you to specify additional test script using closure. For example,

```
step{
    //bind variables
    boolean regularSearch = bind("regularSearch")
    def phoneNumber = bind("fs4googlesearch.phoneNumber")
    String input = bind("input")

    openUrl "http://www.google.com"
    type "google_start_page.searchbox", input
    pause 500
    click "google_start_page.googlesearch"
    waitForPageToLoad 30000
}
```

But this usually implies that the input data format is unique or the test script knows about what format the current input data are using.