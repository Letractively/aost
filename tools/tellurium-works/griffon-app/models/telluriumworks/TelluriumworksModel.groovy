package telluriumworks

import groovy.beans.Bindable
import javax.swing.event.ChangeListener
import griffon.beans.Listener
import javax.swing.event.ChangeEvent

class TelluriumworksModel implements ChangeListener {

  @Bindable @Listener(mvcUpdater)
  String mvcId

  // binding proxy
  final DocumentProxy documentProxy = new DocumentProxy()

  @Bindable String consoleText

  @Bindable String serverStatus = "Not Running"
//  @Bindable ServerConfig serverConfig
  
  @Bindable boolean local = true
  @Bindable String port = "4444"
  @Bindable String profile = ""
  @Bindable boolean multipleWindow = false

//  @Bindable TelluriumConfig telluriumConfig

  @Bindable String browser = "*chrome"
  @Bindable String serverHost = "localhost"
  @Bindable String serverPort = "4444"
  @Bindable String macroCmd = "5"
  @Bindable String option = ""
  @Bindable boolean useTrace = false
  @Bindable boolean useScreenShot = false
  @Bindable boolean useTelluriumEngine = false
  @Bindable String locale = "en_US"


  // listens to changes on the mvcId property
  private mvcUpdater = { e ->
    Document document = null
    if (e.newValue) {
      document = app.models[e.newValue].document
    } else {
      document = new Document()
    }
    documentProxy.document = document
  }

  // listens to tab selection; updates the mvcId property
  void stateChanged(ChangeEvent e) {
    int selectedIndex = e.source.selectedIndex
    if (selectedIndex < 0) {
      setMvcId(null)
    } else {
      def tab = e.source[selectedIndex]
      setMvcId(tab.getClientProperty('mvcId'))
    }
  }
}