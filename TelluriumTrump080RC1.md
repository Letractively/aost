

# Introduction #

The Tellurium Automated Testing Framework (Tellurium) is an open source automated testing framework for web applications. Most web testing tools/frameworks focus on individual UI elements such as links and buttons. Tellurium takes a new approach for automated web testing using the UI module. The UI module makes it possible to build locators for UI elements at runtime. First, this makes Tellurium robust and responsive to changes from internal UI elements. Second, the UI module makes Tellurium expressive. A UI element can be referred to simply by appending the names (uid) along the path to the specific element. This also enables Tellurium's "Group Locating" feature, making composite objects reusable, and addressing dynamic web pages.

Trump 0.8.0 RC1 is out now with a new face as shown in the following diagram.

http://tellurium-users.googlegroups.com/web/TelluriumTrUMPIDE080RC1.png?gda=zN-33k0AAAA7fMi2EBxrNTLhqoq3FzPrZcmj9ObBEZltoHpddi0w2ZgxQ-k5AGcnWXmjzgfIIhjXcoWeEq0kSGuVExhu8O025Tb_vjspK02CR95VRrtmeQ

The new features in 0.8.0 RC1 release include:
  * IFrame support
  * Use Santa Algorithm to locate UI Module
  * Multiple UI Module recording
  * Export file to window
  * Tellurium command test
  * Better help menu
  * Many bug fixes

Please download the xpi file from [here](http://aost.googlecode.com/files/Trump-0.8.0-RC1.xpi).

# Workflow #

The workflow for Trump 0.8.0 RC1 is shown as follows.

http://tellurium-users.googlegroups.com/web/trump080workflow1.png?gda=xBDQUUcAAAA7fMi2EBxrNTLhqoq3FzPrDao5nP8jaxx37jZgfOLElqEMfUVKyjqfWSboqYk_MWEVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw

## Record ##

User clicks on the web to select the UI elements he/she wants to test.

http://tellurium-users.googlegroups.com/web/TrumpRecord080RC1.png?gda=d02hdUcAAAA7fMi2EBxrNTLhqoq3FzPrKbXpwEZ0upKiIDi8jZ_4YTImgURWdne04a7G1I0xo7wVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw

## Generate ##

User generates the UI module by clicking the "generate" button.

http://tellurium-users.googlegroups.com/web/TrumpGenerate080RC1.png?gda=GuTF9EkAAAA7fMi2EBxrNTLhqoq3FzPrKbXpwEZ0upKiIDi8jZ_4YX8odAc7WbnHftHM4HFsUMY2sBRolEB8ByVBmDcVao56hAioEG5q2hncZWbpWmJ7IQ

## Customize ##

Customize the UI module and change the UID and other attributes.

http://tellurium-users.googlegroups.com/web/TrumpCustomize080RC1.png?gda=q5oeqUoAAAA7fMi2EBxrNTLhqoq3FzPrKbXpwEZ0upKiIDi8jZ_4YQIGjRj9EhpQ5rtEk3tLMBaLMZf-fzbEHilZUJS3izcJ_e3Wg0GnqfdKOwDqUih1tA

## Add ##

Add the UI module to cache and start a new recording procedure. The source code is also shown in a window.

http://tellurium-users.googlegroups.com/web/TrumpAdd080RC1.png?gda=vcyas0QAAAA7fMi2EBxrNTLhqoq3FzPrKbXpwEZ0upKiIDi8jZ_4YWIj7nZA40OLyUPTzghpTtZV6u9SiETdg0Q2ffAyHU-dzc4BZkLnSFWX59nr5BxGqA

## Test ##

Trump 0.8.0 RC1 also provides a UI to test Tellurium new Engine APIs.

http://tellurium-users.googlegroups.com/web/TrumpTest080RC1.png?gda=0_jqrUUAAAA7fMi2EBxrNTLhqoq3FzPrKbXpwEZ0upKiIDi8jZ_4YdmzYRKiQE7vX8wErRkHijlzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A

# What's Next #

  * Automatically generate Tellurium DSL test scripts.


# Resources #

  * [Tellurium Project Home](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium Developer Group](http://groups.google.com/group/tellurium-developers)
  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)
  * [Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Released)