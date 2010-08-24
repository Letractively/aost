package telluriumworks

import groovy.beans.Bindable
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.beans.PropertyChangeEvent
import griffon.beans.Listener

//@groovy.beans.Bindable
@Listener(snoopAll)
class FilePanelModel{
   def controller
  
   File loadedFile
//   @Bindable Document document
//   @griffon.beans.Listener(documentUpdater)
//   @Listener({snoopAll(it)})
   @Bindable String fileText
   @Bindable boolean dirty
   @Bindable boolean enabled
   String mvcId

  def snoopAll = { e ->
    logger.debug("Event propertyName: " + e.propertyName + ", newValue: " + e.newValue)
/*    if (e.propertyName == "dirty" && e.newValue) {
      app.models.telluriumworks.dirty = e.newValue
      app.models.telluriumworks.fileText = this.fileText
      logger.debug("fileText: " + this.fileText)
      logger.debug("dirty: " + e.newValue)
    }*/
    if(e.propertyName == "fileText"){
      app.models.telluriumworks.fileText = e.newValue
      app.models.telluriumworks.dirty = this.dirty
      logger.debug("fileText: " + e.newValue)
      logger.debug("dirty: " + this.dirty)

    }
  } as PropertyChangeListener

  private documentListener = { e ->
//    this[(e.propertyName)] = e.newValue
    logger.debug("Event propertyName: " + e.propertyName + ", newValue: " + e.newValue)
    if(e.propertyName == "dirty"){
      app.models.telluriumworks.dirty = e.newValue
      app.models.telluriumworks.fileText = this.fileText
      logger.debug("fileText: " + this.fileText)
      logger.debug("dirty: " + e.newValue)
    }

  } as java.beans.PropertyChangeListener

  private documentUpdater = { e ->
    logger.debug("Event: " + e)
    logger.debug("Event propertyName: " + e.propertyName + ", oldValue: " + e.oldValue)
    logger.debug("Event propertyName: " + e.propertyName + ", newValue: " + e.newValue)
    e.oldValue?.removePropertyChangeListener(documentListener)
    e.newValue?.addPropertyChangeListener(documentListener)
//    ['loadedFile', 'fileText', 'dirty', 'enabled', 'mvcId'].each { prop -> this[prop] = e.newValue?.getAt(prop) }
//     e.source.addPropertyChangeListener(documentListener)
  }

  void propertyChange(PropertyChangeEvent e) {
    logger.debug("Event: " + e)
    logger.debug("Event propertyName: " + e.propertyName + ", oldValue: " + e.oldValue)
    logger.debug("Event propertyName: " + e.propertyName + ", newValue: " + e.newValue)
    if(e.propertyName == "dirty"){
      app.models.telluriumworks.dirty = e.newValue
      app.models.telluriumworks.fileText = this.fileText
      logger.debug("fileText: " + this.fileText)
      logger.debug("dirty: " + e.newValue)
    }

  }
}
