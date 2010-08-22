package telluriumworks

import groovy.beans.Bindable

class TelluriumworksModel {
  @Bindable int tabSelected = 1
  @Bindable boolean enabled
  File loadedFile
  @Bindable String fileText
  @Bindable boolean dirty
  @Bindable String mode
  @Bindable String port
  @Bindable String profile
  @Bindable String serverPort
  @Bindable String serverHost
  @Bindable String macroCmd
  @Bindable String option
 
}