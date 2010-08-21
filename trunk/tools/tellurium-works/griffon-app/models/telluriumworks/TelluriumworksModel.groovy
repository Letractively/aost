package telluriumworks

import groovy.beans.Bindable

class TelluriumworksModel {
   // @Bindable String propName
//  @Bindable String fileName = ""
  @Bindable int tabSelected = 1
  @Bindable boolean enabled
  File loadedFile
  @Bindable String fileText
  @Bindable boolean dirty
  
}