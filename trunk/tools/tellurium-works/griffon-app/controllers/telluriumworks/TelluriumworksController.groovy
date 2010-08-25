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
  }

  def runFile = {
    view.mainPane.selectedIndex = 0
    execOutside {
      String script = model.documentProxy.document.contents
      if(script != null && (!script.isEmpty())){
        clogger.log("Running file " + model.documentProxy.document.title)
        telluriumService.runScript(script)
      }else{
        clogger.log("No file to run")
      }
    }
  }

  def saveFile = {
    view.mainPane.selectedIndex = 0
    clogger.log("Saving file " + model.documentProxy.document.title)
    app.controllers[model.mvcId].saveFile(it)
  }

  def closeFile = {
    view.mainPane.selectedIndex = 0
    clogger.log("Closing file " + model.documentProxy.document.title)
    app.controllers[model.mvcId].closeFile(it)
  }

  def quit = {
    clogger.log("Quitting...")
    app.shutdown()
  }

  def runSeleniumServer = {
    execOutside {
//    def conf = model.serverConfig
      def conf = new ServerConfig()
      conf.local = model.local
      conf.profile = model.profile
      conf.port = model.port
      conf.multipleWindow = model.multipleWindow

      clogger.log("Run Selenium server with configuration: " + conf.toString())

      telluriumService.runSeleniumServer(conf)
    }
  }

  def stopSeleniumServer = {
    execOutside {
      clogger.log("Stop Selenium server")
      telluriumService.stopSeleniumServer()
    }
  }

  def updateTelluriumConfig = {
    execOutside {
      TelluriumConfig conf = new TelluriumConfig()
      conf.browser = model.browser
      conf.serverHost = model.serverHost
      conf.serverPort = model.serverPort
      conf.macroCmd = model.macroCmd
      conf.option = model.option
      conf.useTrace = model.useTrace
      conf.useScreenShot = model.useScreenShot
      conf.locale = model.locale

      clogger.log("Update Tellurium Configuration to " + conf.toString())

      telluriumService.updateTelluriumConfig(conf)
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