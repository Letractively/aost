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

  @Bindable String mode
  @Bindable String port
  @Bindable String profile
  @Bindable String serverPort
  @Bindable String serverHost
  @Bindable String macroCmd
  @Bindable String option

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