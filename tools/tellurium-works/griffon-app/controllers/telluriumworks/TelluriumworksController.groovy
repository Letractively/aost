package telluriumworks

import javax.swing.JFileChooser

class TelluriumworksController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
    }

   def openFile = {
      def openResult = view.fileChooserWindow.showOpenDialog(view.fileViewerWindow)
      if( JFileChooser.APPROVE_OPTION == openResult ) {
         File file = new File(view.fileChooserWindow.selectedFile.toString())
         // let's calculate an unique id for the next mvc group
         String mvcId = file.path + System.currentTimeMillis()
         createMVCGroup("FilePanel", mvcId,
            [file: file, tabGroup: view.tabGroup, tabName: file.name, mvcId: mvcId])
      }
   }

   def quit = {
      app.shutdown()
   }

   def runSeleniumServer = {

   }

   def stopSeleniumServer = {

   }

   def setTelluriumConfig = {

   }

    /*
    def action = { evt = null ->
    }
    */
}