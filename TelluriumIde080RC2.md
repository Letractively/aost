# Introduction #

Tellurium is an expressive testing framework and it uses [UI modules](http://code.google.com/p/aost/wiki/UserGuide070UIObjects#UI_Module) to define the web UIs under testing. [UIDs](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#UiID_Attribute) are used to reference UI elements instead of hardcoded locators.

Tellurium IDE is a Firefox plugin to record user actions and automatically generate Tellurium DSL script. Tellurium IDE is developed from the codebase of Tellurium UI Module Firefox Plugin  [Trump](http://code.google.com/p/aost/wiki/UserGuide070TelluriumSubprojects#Tellurium_UI_Module_Plugin_%28TrUMP%29), which is used to record user's selection of UIs and automatically generate UI modules. Tellurium IDE is a step further to automatically generate Tellurium commands and UI modules. The exported Groovy DSL script could be run directly with [TelluriumWorks](http://code.google.com/p/aost/wiki/TelluriumWorks080RC1).

[Tellurium IDE 0.8.0 RC1](http://code.google.com/p/aost/wiki/TelluriumIde080RC1) was released in August 2010. A tutorial video is available on [youTube](http://www.youtube.com/watch?v=yVIBY8QzWzE). There are a lot of improvement since RC1 was release and thus we release RC2.

# Download #

Tellurium IDE 0.8.0 RC2 can be downloaded from [Tellurium Download site](http://aost.googlecode.com/files/TelluriumIDE-0.8.0-RC2.xpi) or from [Firefox add-on site](https://addons.mozilla.org/en-US/firefox/addon/217284/).

Note: if you got "Download Error -228" from firefox add-on site. Please uninstall Tellurium IDE first, and then install the new version. Or right click install button, "save link as" download xpi to desktop, then File > Open File, browse to xpi, open to install.

# Features #

Tellurium 0.8.0 RC2 has the following update.

  1. Changed the JavaScript event record mechanism
  1. Fixed Mac OS X spinning cursor problem
  1. Updated UI module generation algorithm
  1. Improved UI layout on Windows systems
  1. Other bug fixes and improvement

# What's Next #

Be aware that Tellurium IDE 0.8.0 RC2 is work in process, please report any problems back to [Tellurium user group](http://groups.google.com/group/tellurium-users).

We will work on Tellurium UI module generation algorithm to automatically generate UI templates such as List and Table.

We are looking for JavaScript experts to join our team to help with the Tellurium IDE and Tellurium Engine development. If you are interested, please send emails to telluriumsource at gmail dot com.

Thanks in advance,

Tellurium Team

# Resources #

  * [Tellurium IDE on Firefox addon site](https://addons.mozilla.org/en-US/firefox/addon/217284/)
  * [Tellurium IDE 0.8.0 RC1 tutorial video](http://www.youtube.com/watch?v=yVIBY8QzWzE/)
  * [Tellurium Project Home](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)