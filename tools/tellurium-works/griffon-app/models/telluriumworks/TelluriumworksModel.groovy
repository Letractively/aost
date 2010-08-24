package telluriumworks

import groovy.beans.Bindable

@groovy.beans.Bindable
class TelluriumworksModel {
  @Bindable int tabSelected = 1

  File loadedFile
  String fileText
  boolean dirty
  boolean enabled
  String mvcId

  @Bindable String mode
  @Bindable String port
  @Bindable String profile
  @Bindable String serverPort
  @Bindable String serverHost
  @Bindable String macroCmd
  @Bindable String option
  
//  @griffon.beans.Listener(documentUpdater) 
//  FilePanelModel filePanelModel

/*  private documentListener = { e ->
    this[(e.propertyName)] = e.newValue
    logger.debug("Event propertyName: " + e.propertyName + ", newValue: " + e.newValue)
  } as java.beans.PropertyChangeListener

  private documentUpdater = { e ->
    e.oldValue?.removePropertyChangeListener(documentListener)
    e.newValue?.addPropertyChangeListener(documentListener)
    ['loadedFile', 'fileText', 'dirty', 'enabled', 'mvcId'].each { prop -> this[prop] = e.newValue?.getAt(prop) }
  }*/
}