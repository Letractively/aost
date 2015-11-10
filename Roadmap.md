# Roadmap #

## Tellurium Core ##

For the the next release of Tellurium Core 0.7.0, we should achieve the following goals:

  * Command bundle
  * UI module caching
  * Widget architecture re-visit
  * Built in support for trace information such as timing.
  * More flexible name schema for UI templates, for example, you can use "odd" and "even" for the name
  * Add Exception code in Engine so that the Core can handle different types of exceptions appropriately.
  * Add table header alias so that you can use header alias to refer to a column
  * Add I18N support
  * Add test utility packages such as testing cross site scripts, SQL injections, mock objects, and more.
  * Built in support for test result reporting (including captured screenshot names)
  * Add waitForAjax method
  * Add takeSnapshot(uid) method to get back the snapshot of the UI Module from Engine

## Engine ##

For the Engine project 0.1.0, we should
  * Command bundle
  * UI module caching
  * Design and implement an advanced algorithm for group locating
  * Re-implement Selenium API methods using jQuery

## TrUMP ##

For the TrUMP project 0.2.0, we should
  * Add support for IFrames/Frames
  * Add support to record dialogs/popups
  * Add Inference engine to derive UI templates (May be deferred to 0.3.0)

## Widget Extensions ##

For the DOJO widget 0.1.0, we should
  * Make the Widget reconfigurable in definition
  * Add more Dojo widgets
  * Add more examples
  * Add documents

## Reference Projects ##

For reference projects, we should
  * Add our own sandbox so that the tests can be repeatable.
  * Create more examples to illustrate all features and the usage of Tellurium UI objects.

## Others ##

  * Add Widget Maven archetype
  * Add Tellurium Maven help archetype
  * Add behavior driven test support (Integrate with easyB or implement our own BDT)
  * Add more support for functional test
  * Add IDE plugins
  * Add Grails Tellurium plugin
  * Integration Fitnesse with Tellurium