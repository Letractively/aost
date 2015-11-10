(A PDF version of the user guide is available [here](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf))



# Tellurium Architecture #


Tellurium architecture is shown in the following diagram.

http://tellurium-users.googlegroups.com/web/telluriumnewarchitecture070.png?gda=0Cry3VEAAAA7fMi2EBxrNTLhqoq3FzPraJRXcLj8Noz0FE1yhGyZ4Cj9p0mTDab5g6bwssabHmxXgg0UR0O9X9-Irzu_uB8AUwk_6Qi3BU8HCN0q6OYwM5VxXgp_nHWJXhfr7YhqVgA&gsc=jkTIIgsAAACI9y1tsPXdLT5_3omT2FAm

There are two major parts, i.e., the Tellurium Core, which does Java/Groovy object to runtime locator mapping, event handling, and command bundling. The Tellurium Engine is embedded inside the Selenium server and is a test driving engine for Tellurium. The two are connected by Selenium RC.

The DSL parser consists of the DSL Object Parser, Object Builders, and the Object Registry.

Using Groovy builder pattern, UI objects are defined expressively and in a nested fashion. The DSL object parser parses the DSL object definition recursively and uses object builders to build the objects on the fly. An object builder registry is designed to hold all predefined UI object builders in the Tellurium framework, and the DSL object parser looks at the builder registry to find the appropriate builders.

Since the registry is a hash map, you can override a builder with a new one using the same UI name. Users can also add their customer builders into the builder registry. The DSL object definition always comes first with a container type object. An object registry (a hash map) is used to store all top level UI Objects. As a result, for each DSL object definition, the top object IDs must be unique in the DslContext. The object registry is used by the framework to search for objects by their IDs and fetch objects for different actions.

The Object Locator Mapping (OLM) is the core of the Tellurium framework and it includes UI ID mapping, XPath builder, jQuery selector builder, and Group Locating.

The UI ID supports nested objects. For example, "menu.wiki" stands for a URL Link "wiki" inside a container called "menu".

The UI ID also supports one-dimensional and two-dimensional indices for tables and lists. For example, `"main.table[2][3]"` stands for the UI object of the 2nd row and the 3rd column of a table inside the container "main".

XPath builder builds the XPath from the composite locator. For example, a set of attributes. Starting with version 0.6.0, Tellurium supports jQuery selectors to address the problem of poor performance of XPath in Internet Explorer.

jQuery selector builders are used to automatically generate jQuery selectors instead of XPath with the following advantages:

  * Provides faster performance in IE
  * Leverages the power of jQuery to retrieve bulk data from the web by testing with one method call
  * Provides new features using jQuery attribute selectors

The Group Locating Concept (GLC) exploits the group information inside a collection of UI objects to assist in finding the locator of the UI objects collection.

The Eventhandler handles all events such as "click", "type", "select", etc.

The Data Accessor fetches data or UI status from the DOM. The dispatcher delegates all calls it receives from the Eventhandler and the data accessor attached to the connector is also connected to the Tellurium engine.

The dispatcher is designed to decouple the rest of the Tellurium framework from the base test driving engine so that it can be switched to a different test driving engine by simply changing the dispatcher logic.

# How Tellurium Works #

Tellurium works in two mode. The first one is to work as a wrap of the Selenium framework.

http://tellurium-users.googlegroups.com/web/TelluriumRunningMode1.png?gda=qNEKqUsAAAA7fMi2EBxrNTLhqoq3FzPro_2kwg16ExMe9wfbYSgXCQdoVt8RgM6Q8hpOVtSHouVBqckrS9zbalJdCfH3I_v9BkXa90K8pT5MNmkW1w_4BQ&gsc=jkTIIgsAAACI9y1tsPXdLT5_3omT2FAm

That is to say, Tellurium core generates the runtime locator based on the attributes in a UI module and then pass the selenium calls to Selenium core with Tellurium extensions.

Tellurium is evolving to its own test driving Engine and the work mode is different as shown in the following sequence diagram.

http://tellurium-users.googlegroups.com/web/TelluriumRunningMode2.png?gda=kwN3wEsAAAA7fMi2EBxrNTLhqoq3FzPro_2kwg16ExMe9wfbYSgXCQdoVt8RgM6Q8hpOVtSHouWqzYvvFLF4HH6kcFg68ZqyBkXa90K8pT5MNmkW1w_4BQ&gsc=jkTIIgsAAACI9y1tsPXdLT5_3omT2FAm

The Tellurium Core will convert the UI module into a JSON representation and pass it to Tellurium Engine for the first time when the UI module is used. The Tellurium Engine uses the Santa algorithm to locate the whole UI module and put it into a cache. For the sequent calls, the cached UI module will be used instead of locating them again. Another new features in Tellurium 0.7.0 is the [Macro Command](http://code.google.com/p/aost/wiki/Tellurium070Update#Macro_Command), which combine multiple commands into one batch and then send them to Tellurium engine in one call to reduce the round trip latency. For more new features in 0.7.0, please read [What's New in Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Update).