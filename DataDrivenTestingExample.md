## Data Driven Testing Examples ##

To demonstrate data driven testing, we take GoogleBookList and GoogleCodeHosting as examples. First, we create two test modules, i.e., the GoogleBookListModule

```
class GoogleBookListModule extends TelluriumDataDrivenModule{

    public void defineModule() {
        //define UI module
        ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true") {
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "/p") {
                UrlLink(uid: "all", locator: "/a")
            }
        }

        //define file data format
        fs.FieldSet(name: "GoogleBookList", description: "Google Book List") {
            Test(value: "checkBookList")
            Field(name: "category", description: "book category")
            Field(name: "size", type: "int", description: "google book list size ")
        }

        defineTest("checkBookList"){
            def expectedCategory = bind("GoogleBookList.category")
            def expectedSize = bind("GoogleBookList.size")
            openUrl("http://books.google.com/")
            String category = getText("GoogleBooksList.category")
            compareResult(expectedCategory, category)

            int size = getListSize("GoogleBooksList.subcategory")
            //customize the compareResult and override its behaviour 
            compareResult(expectedSize, size){
                assertTrue(expectedSize == size)
            }
        }
    }
}
```

and the GoogleCodeHostingModule

```
class GoogleCodeHostingModule extends TelluriumDataDrivenModule{

    public void defineModule() {
       ui.Table(uid: "labels_table", clocator: [:], group: "true"){
         TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
         Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
             UrlLink(uid: "all", locator: "/a")
           }
       }

        //define file data format
        fs.FieldSet(name: "GCHStatus", description: "Google Code Hosting input") {
            Test(value: "getGCHStatus")
            Field(name: "label")
            Field(name: "rowNum", type: "int")
            Field(name: "columNum", type: "int")
        }

        fs.FieldSet(name: "GCHLabel", description: "Google Code Hosting input") {
            Test(value: "clickGCHLabel")
            Field(name: "row", type: "int")
            Field(name: "column", type: "int")
        }

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

       defineTest("clickGCHLabel"){
           def row = bind("GCHLabel.row")
           def column = bind("GCHLabel.column")
           
           openUrl("http://code.google.com/hosting/")
           click  "labels_table[2][1].[${row}][${column}]"

           waitForPageToLoad 30000
       }
    }
}
```

as shown above, each module defines its own Ui modules, FieldSets (i.e., input data format), and tests. Then, we need to define the actual testing class GoogleBookListCodeHostTest,

```
class GoogleBookListCodeHostTest extends TelluriumDataDrivenTest{

    public void testDataDriven() {

        includeModule  example.test.ddt.GoogleBookListModule.class
        includeModule  example.test.ddt.GoogleCodeHostingModule.class

        //load file
        loadData "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt"
        
        //read each line and run the test script until the end of the file
        stepToEnd()

        //close file
        closeData()

    }
}
```

This test class simply load the GoogleBookListModule and the GoogleCodeHostingModule, then load the input data from file "src/test/example/test/ddt/GoogleBookListCodeHostInput.txt". After that, it uses "stepToEnd" to drive the testing until reach the end of the file. Finally, it use "closeData" to close the input data stream and output the test results in XML format in console.

The final piece is the input file, which is defined as follows,

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

When all the above are done, just run the GoogleBookListCodeHostTest class as a regular JUnit test and it will run all the tests for you.

The test results are shown as follows (Here the time unit is second),

```
<TestResults>
  <Total>7</Total>
  <Succeeded>5</Succeeded>
  <Failed>2</Failed>
  <Test name='checkBookList'>
    <Step>1</Step>
    <Passed>true</Passed>
    <Input>
      <test>checkBookList</test>
      <category>Fiction</category>
      <size>8</size>
    </Input>
    <Assertion Expected='Fiction' Actual='Fiction' Passed='true' />
    <Assertion Expected='8' Actual='8' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>5.602938</Runtime>
  </Test>
  <Test name='checkBookList'>
    <Step>2</Step>
    <Passed>false</Passed>
    <Input>
      <test>checkBookList</test>
      <category>Fiction</category>
      <size>3</size>
    </Input>
    <Assertion Expected='Fiction' Actual='Fiction' Passed='true' />
    <Assertion Expected='3' Actual='8' Passed='false' Error='' />
    <Status>PROCEEDED</Status>
    <Runtime>1.653998</Runtime>
  </Test>
  <Test name='getGCHStatus'>
    <Step>3</Step>
    <Passed>true</Passed>
    <Input>
      <test>getGCHStatus</test>
      <label>Example project labels:</label>
      <rowNum>3</rowNum>
      <columNum>6</columNum>
    </Input>
    <Assertion Expected='Example project labels:' Actual='Example project labels:' Passed='true' />
    <Assertion Expected='3' Actual='3' Passed='true' />
    <Assertion Expected='6' Actual='6' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>3.163041</Runtime>
  </Test>
  <Test name='getGCHStatus'>
    <Step>4</Step>
    <Passed>false</Passed>
    <Input>
      <test>getGCHStatus</test>
      <label>Example project</label>
      <rowNum>3</rowNum>
      <columNum>6</columNum>
    </Input>
    <Assertion Expected='Example project' Actual='Example project labels:' Passed='false' Error='expected:&lt;...&gt; but was:&lt;... labels:&gt;' />
    <Assertion Expected='3' Actual='3' Passed='true' />
    <Assertion Expected='6' Actual='6' Passed='true' />
    <Status>PROCEEDED</Status>
    <Runtime>2.115711</Runtime>
  </Test>
  <Test name='clickGCHLabel'>
    <Step>5</Step>
    <Passed>true</Passed>
    <Input>
      <test>clickGCHLabel</test>
      <row>1</row>
      <column>1</column>
    </Input>
    <Status>PROCEEDED</Status>
    <Runtime>2.664228</Runtime>
  </Test>
  <Test name='clickGCHLabel'>
    <Step>6</Step>
    <Passed>true</Passed>
    <Input>
      <test>clickGCHLabel</test>
      <row>2</row>
      <column>2</column>
    </Input>
    <Status>PROCEEDED</Status>
    <Runtime>3.277487</Runtime>
  </Test>
  <Test name='clickGCHLabel'>
    <Step>7</Step>
    <Passed>true</Passed>
    <Input>
      <test>clickGCHLabel</test>
      <row>3</row>
      <column>3</column>
    </Input>
    <Status>PROCEEDED</Status>
    <Runtime>0.985857</Runtime>
  </Test>
</TestResults>

```