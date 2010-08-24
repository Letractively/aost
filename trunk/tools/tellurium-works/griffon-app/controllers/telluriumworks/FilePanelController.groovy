package telluriumworks

class FilePanelController {
   def model
   def view

   void mvcGroupInit(Map args) {
      model.document = args.document
      model.mvcId = args.mvcId
      execOutside {
         // load the file's text, outside the EDT
         String text = model.document.file.text
         // update the model inside the EDT
         execAsync { model.document.contents = text }
      }
   }

   def runFile = {
      def filename = view.editor.text;
      logger.info("Start to run Tellurium Test ${model.loadedFile}");

      doOutside {
 	    doLater{
//          view.consoleTxt.text = view.consoleTxt.text + "Start to run Tellurium Test ${model.loadedFile}"

        }
/*         // write text to file, outside the EDT
         model.loadedFile.text = view.editor.text
         // update model.text, inside EDT
         doLater { model.fileText = view.editor.text }
*/
      }
   }
  
   def saveFile = {
//      println "model: " + model + ", view: " + view
//      doOutside {
//         // write text to file, outside the EDT
//         model.document.file.text = view.editor.text
//         // update model.text, inside EDT
//         doLater { model.document.contents = view.editor.text }
//      }
     
       execOutside {
         // write text to file, outside the EDT
         model.document.file.text = view.editor.text
         // update model.text, inside EDT
         execAsync { model.document.contents = view.editor.text }
      }
   }

   def closeFile = {
 //    println "model: " + model + ", view: " + view
//     println "view.tabGroup: " + view.tabGroup + ", view.tab: " + view.tab
      // remove tab
      view.tabGroup.remove view.tab
      // cleanup
      destroyMVCGroup model.mvcId
   }
}
