       //define UI module
       ui.Container(uid: "GoogleBooksList", clocator: [tag: "table", id: "hp_table"], group: "true") {
            TextBox(uid: "category", clocator: [tag: "div", class: "sub_cat_title"])
            List(uid: "subcategory", clocator: [tag: "div", class: "sub_cat_section"], separator: "/p") {
                UrlLink(uid: "all", locator: "/a")
            }
       }

       ui.Table(uid: "labels_table", clocator: [:], group: "true"){
         TextBox(uid: "row: 1, column: 1", clocator: [tag: "div", text: "Example project labels:"])
         Table(uid: "row: 2, column: 1", clocator: [header: "/div[@id=\"popular\"]"]){
             UrlLink(uid: "all", locator: "/a")
           }
       }

        //define file data format
        fs.FieldSet(name: "GoogleBookList", description: "Google Book List") {
            Test(value: "checkBookList")
            Field(name: "category", description: "book category")
            Field(name: "size", type: "int", description: "google book list size ")
        }

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

        defineTest("checkBookList"){
            def expectedCategory = bind("GoogleBookList.category")
            def expectedSize = bind("GoogleBookList.size")
            openUrl("http://books.google.com/")
            String category = getText("GoogleBooksList.category")
            compareResult(expectedCategory, category)

            int size = getListSize("GoogleBooksList.subcategory")
            compareResult(expectedSize, size){
                assertTrue(expectedSize == size)
            }
        }


        //load file
        loadData "src/test/resources/example/test/ddt/GoogleBookListCodeHostInput.txt"
//        loadData "src/test/resources/example/test/ddt/GoogleBookListCodeHostCSV.txt"

        //read each line and run the test script until the end of the file
        stepToEnd()
                                                                                       
                                                                                         
        //close file
        closeData()
