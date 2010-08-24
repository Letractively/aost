package telluriumworks

import groovy.beans.Bindable
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import java.beans.PropertyChangeEvent
import griffon.beans.Listener

class FilePanelModel{

   @Bindable Document document

/*   File loadedFile
   @Bindable String fileText
   @Bindable boolean dirty
   @Bindable boolean enabled*/
   String mvcId
}
