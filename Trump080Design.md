

# Introduction #

One significant add-on to Trump 0.8.0 is the ability to record user actions and convert them into Tellurium DSL scripts. The following sections cover the design issues for Trump 0.8.0.

## Workflow ##

http://tellurium-developers.googlegroups.com/web/trumpworkflow.png?gda=9farzUMAAAC3f0S777RoMipgwttRc5E0rE3sb7K01jCxhcA7IRHMaYREyMfmzRjWt2IyFXBxC30ytiJ-HdGYYcPi_09pl8N7FWLveOaWjzbYnpnkpmxcWg

The workflow is shown as above:
  1. User acts on the web and Trump records the events
  1. The events are stored as commands with fields such as name, value, and reference id
  1. In the meanwhile, the dom node and its reference id are pushed into a list
  1. User select on the web and Trump also records the events
  1. The event does not have a command associated with it and is only pushed to the DOM list
  1. An inner tree is built based on the dom list
  1. The UI module is generated from the inner tree
  1. At the same time, a reference id to UID map is created
  1. The UI modules are put into the top of output script file
  1. The reference ids in the command list are replaced with their UIDs
  1. The updated command list is turned into Tellurium DSL script

## UI Design ##

The UI should be able to achieve following goals.

### Record ###
  1. User works on the web and Trump records both the actions and the dom nodes
  1. User is able to stop recording actions and click on UI elements so that they are included in the UI module
  1. User can de-select UI elements
  1. UI shows the array of selected UI elements
  1. UI shows the recorded command history
  1. UI should provide user a mechanism to switch between action recording and UI selection modes

### Generate ###
  1. Based on the UI elements recorded with the actions or selected by users, generate the UI module
  1. Generate the test script
  1. UI shows the generated UI Module
  1. UI shows the recorded DSL script
  1. Show UI module validation result

### Customize ###
  1. Since one record may include multiple UI modules, user should be able to select which UI module to customize
  1. User select on the UI module, UI show all details
  1. User changes UI object variables
  1. User saves the UI object
  1. UI shows the customized UI module
  1. User is able to select one UI object and shows it on the web page
  1. User may be able to customize the DSL script, for example, change the command name or insert additional commands
  1. User saves the DSL script
  1. UI shows the customized DSL script

### Replay ###
  1. User should be able to re-run the record DSL script
  1. Show the results
  1. Indicate the failed commands

### Export ###
  1. User should be able to view the generate Test script (include the UI modules at the top of the file) on window to cut and paste
  1. User can export the test script to file system
  1. User may be able to export to different formats (for future)
  1. UI should be able to provide user the option of different formats.

### Log ###
  1. Trump should be able to log message in different levels
  1. User should be able to select different log levels
  1. User may like to hide log window

### Others ###
  1. Trump should include option settings
  1. Trump should provide Help menus
  1. Trump should provide close button

## Prototype ##

### Record View ###

http://tellurium-users.googlegroups.com/web/TelluriumWorksIDE1.png?gda=-yrlWUgAAADzylifMuGHtadsGrIEvqZXnuFlQ4sjDFZCE4NQifii9Hb2_5J0TLOkw11eWofx4EYlzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=rAad1gsAAADFH8J-b2rEm5GxhB3sUBbj

### Command View ###

http://tellurium-users.googlegroups.com/web/TelluriumWorksIDE2.png?gda=Vi8lLUgAAADzylifMuGHtadsGrIEvqZXnuFlQ4sjDFZCE4NQifii9BZNPNx8uVC2QKwLvOaOtKQlzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=rAad1gsAAADFH8J-b2rEm5GxhB3sUBbj

### Source View ###

http://tellurium-users.googlegroups.com/web/TelluriumWorksIDE3.png?gda=x5nOG0gAAADzylifMuGHtadsGrIEvqZXnuFlQ4sjDFZCE4NQifii9N7xIhvF9sdWrXJWNoMZrtclzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=rAad1gsAAADFH8J-b2rEm5GxhB3sUBbj