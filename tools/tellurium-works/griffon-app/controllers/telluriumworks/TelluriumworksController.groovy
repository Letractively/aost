package telluriumworks

import javax.swing.JFileChooser

import org.jdesktop.swingx.JXTipOfTheDay
import org.jdesktop.swingx.tips.TipLoader

class TelluriumworksController {
  // these will be injected by Griffon

  def model
  def view
  def telluriumService

  JXTipOfTheDay totd
  def clogger = ConsoleLogger.instance

  void mvcGroupInit(Map args) {
    ConsoleLogger.instance.view = view
    // this method is called after model and view are injected
//    new Hello().sayHello()
  }

  def openFile = {

    def openResult = view.fileChooserWindow.showOpenDialog(view.mainWindow)
    if (JFileChooser.APPROVE_OPTION == openResult) {
      File file = new File(view.fileChooserWindow.selectedFile.toString())
      // let's calculate an unique id for the next mvc group
      String mvcId = file.path + System.currentTimeMillis()

      createMVCGroup('FilePanel', mvcId, [
              document: new Document(file: file, title: file.name),
              tabGroup: view.tabGroup,
              tabName: file.name,
              mvcId: mvcId])
    }
    
    view.mainPane.selectedIndex = 0
  }

  def runFile = {
    view.mainPane.selectedIndex = 0
    execOutside {
      String script = model.documentProxy.document.contents
      if(script != null && (!script.isEmpty())){
        clogger.log("Running file " + model.documentProxy.document.title)
        try{
          telluriumService.runScript(script)
          clogger.log("Test done!")
        }catch(Exception e){
          clogger.log("Test failed because of Error " + e.getMessage())
        }
      }else{
        clogger.log("No file to run")
      }
    }
  }

  def saveFile = {
    view.mainPane.selectedIndex = 0
    if(model.mvcId != null){
      clogger.log("Saving file " + model.documentProxy.document.title)
      app.controllers[model.mvcId].saveFile(it)
    }else{
      clogger.log("No file to save")
    }
  }

  def closeFile = {
    view.mainPane.selectedIndex = 0
    if(model.mvcId != null){
      clogger.log("Closing file " + model.documentProxy.document.title)
      app.controllers[model.mvcId].closeFile(it)
    }else{
      clogger.log("No file to close")
    }
  }

  def quit = {
    clogger.log("Quitting...")
    app.shutdown()
  }

  def logValidationErrors(errors){
    if(errors != null){
       def msg = "Error message: "
       errors?.each { error ->
         def errorMessage = app.messageSource.getMessage(error.errorCode, error.arguments)
         msg += errorMessage
       }
       clogger.log(msg)
       logger.error(msg)
     }
  }

  def runSeleniumServer = {
    if(!model.serverConfig.validate()){
      doLater {
        clogger.log("Invalid Configuration: " + model.serverConfig.toString())
        logValidationErrors(model.serverConfig.errors)
       }
    }else{
      execOutside {
        def conf = model.serverConfig

        clogger.log("Run Selenium server with configuration: " + conf.toString())

        boolean status = telluriumService.runSeleniumServer(conf)
        view.serverStatus.text = (status ? "Running": "Not Running")
      }
    }
  }

  def stopSeleniumServer = {
    execOutside {
      clogger.log("Stop Selenium server")
      boolean status = telluriumService.stopSeleniumServer()
      view.serverStatus.text = (status ? "Running": "Not Running")
    }
  }

  def updateTelluriumConfig = {
    if (!model.telluriumConfig.validate()) {
      doLater {
        clogger.log("Invalid Configuration: " + model.telluriumConfig.toString())
        logValidationErrors(model.telluriumConfig.errors)
      }
    } else {
      execOutside {
        TelluriumConfig conf = model.telluriumConfig

        clogger.log("Update Tellurium Configuration to " + conf.toString())

        telluriumService.updateTelluriumConfig(conf)
      }
    }
  }

  def help = {

  }

  def showTips = {
    if (!totd) {
      Properties tipsInput = new Properties()
//         def data = this.getClass().getResourceAsStream("tips.properties")
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
    int x = app.windowManager.windows[0].x + (app.windowManager.windows[0].width - dialog.width) / 2
    int y = app.windowManager.windows[0].y + (app.windowManager.windows[0].height - dialog.height) / 2
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
    if (dialog.visible) return
    dialog.pack()
    int x = app.windowManager.windows[0].x + (app.windowManager.windows[0].width - dialog.width) / 2
    int y = app.windowManager.windows[0].y + (app.windowManager.windows[0].height - dialog.height) / 2
    dialog.setLocation(x, y)
    dialog.show()
  }

  def about = {
    def dialog = view."aboutDialog"
    if (dialog.visible) return
    dialog.pack()
    int x = app.windowManager.windows[0].x + (app.windowManager.windows[0].width - dialog.width) / 2
    int y = app.windowManager.windows[0].y + (app.windowManager.windows[0].height - dialog.height) / 2
    dialog.setLocation(x, y)
    dialog.show()
  }

  def clearConsole = {
    execOutside {
      view.consoleTxt.text = ""
      ConsoleLogger.instance.clear()
    }
  }

}