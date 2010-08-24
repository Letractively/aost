package telluriumworks

import javax.swing.JFileChooser

import org.jdesktop.swingx.JXTipOfTheDay
import org.jdesktop.swingx.tips.TipLoader

class TelluriumworksController {
    // these will be injected by Griffon
    def model
    def view
    JXTipOfTheDay totd
    //keep track of the opened file tabs
//    def filePanels = []

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected
      new Hello().sayHello()     
    }

   def openFile = {
      def openResult = view.fileChooserWindow.showOpenDialog(view.mainWindow)
      if( JFileChooser.APPROVE_OPTION == openResult ) {
        File file = new File(view.fileChooserWindow.selectedFile.toString())
        // let's calculate an unique id for the next mvc group
        String mvcId = file.path + System.currentTimeMillis()
//        filePanels.add(mvcId)

        createMVCGroup('FilePanel', mvcId, [
                document: new Document(file: file, title: file.name),
                tabGroup: view.tabGroup,
                tabName: file.name,
             mvcId: mvcId])
//         createMVCGroup("FilePanel", mvcId,
//            [file: file, tabGroup: view.tabGroup, tabName: file.name, mvcId: mvcId])
      }
   }

   def runFile = {
     
   }

   def saveFile = {
      app.controllers[model.mvcId].saveFile(it)

/*      println "tabCount = " + view.tabGroup.tabCount
      println "selectedIndex = " + view.tabGroup.selectedIndex
      println "selectedComponent = " + view.tabGroup.selectedComponent
      println "view.tabGroup = " + view.tabGroup
      println "model.fileText = " + model.fileText
      println "model.dirty = " + model.dirty
      println view.tabGroup.selectedComponent.editor.text
      */


   }

   def closeFile = {
      app.controllers[model.mvcId].closeFile(it)

/*     if (filePanels.size() > 0) {
       def mvcId = filePanels[view.tabGroup.selectedIndex]
       view.tabGroup.remove view.tabGroup.selectedComponent
       filePanels.remove(mvcId)
       logger.debug("Removing File tab: " + mvcId)
       destroyMVCGroup mvcId
     }*/
   }

   def quit = {
      app.shutdown()
   }

   def goMainPage = {

   }

   def goServerPage = {

   }

   def goConfigPage = {

   }

   def runSeleniumServer = {

   }

   def stopSeleniumServer = {

   }

   def setTelluriumConfig = {

   }

   def help = {

   }

   def showTips = {
     if (!totd) {
         Properties tipsInput = new Properties()
//         def data = this.getClass().getResourceAsStream("tips.properties")
//         def data = ClassLoader.getResourceAsStream("tips.properties")
         def data = Thread.currentThread().getContextClassLoader().getResourceAsStream("tips.properties")
         tipsInput.load(data);
         totd = new JXTipOfTheDay(TipLoader.load(tipsInput));
     }
     totd.showDialog(null);
   }

  private void showDialog(dialogName) {
    def dialog = view."$dialogName"
    if (dialog.visible) return
    dialog.pack()
//    int x = app.appFrames[0].x + (app.appFrames[0].width - dialog.width) / 2
//    int y = app.appFrames[0].y + (app.appFrames[0].height - dialog.height) / 2
    int x = app.mainWindow.x + (app.mainWindow.width - dialog.width) / 2
    int y = app.mainWindow.y + (app.mainWindow.height - dialog.height) / 2
    dialog.setLocation(x, y)
    dialog.show()
  }

  private void hideDialog(dialogName) {
    def dialog = view."$dialogName"
    dialog.hide()
  }

  // About
  void showAbout(event) {
      //showDialog("aboutDialog")
      def dialog = view."aboutDialog"
      if( dialog.visible ) return
      dialog.pack()
//      int x = app.appFrames[0].x + (app.appFrames[0].width - dialog.width) / 2
//      int y = app.appFrames[0].y + (app.appFrames[0].height - dialog.height) / 2
      int x = app.mainWindow.x + (app.mainWindow.width - dialog.width) / 2
      int y = app.mainWindow.y + (app.mainWindow.height - dialog.height) / 2
      dialog.setLocation(x, y)
      dialog.show()
  }

   def about = {
     def dialog = view."aboutDialog"
     if( dialog.visible ) return
     dialog.pack()
//     int x = app.appFrames[0].x + (app.appFrames[0].width - dialog.width) / 2
//     int y = app.appFrames[0].y + (app.appFrames[0].height - dialog.height) / 2
     int x = app.mainWindow.x + (app.mainWindow.width - dialog.width) / 2
     int y = app.mainWindow.y + (app.mainWindow.height - dialog.height) / 2
     dialog.setLocation(x, y)
     dialog.show()     
   }

    /*
    def action = { evt = null ->
    }
    */
}