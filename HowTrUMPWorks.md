

## Tellurium Tutorial Series: How does TrUMP work? ##

Tellurium UI Model Plugin (TrUMP) 0.1.0 will be out soon. You may wonder how TrUMP works. To make you better understand TrUMP, we would like to explain a bit about the TrUMP implementation, which is shown as in the following diagram.

http://tellurium-users.googlegroups.com/web/TrUMPDiagram.png?gda=rTWjlUIAAACXZPxEX7Ki-M5C2JpeBoXXyCeWpY-bZ6aKw-b0YTCbUftXpJrTaqHt15X9LsoNgO5V4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=agyN3wsAAAAOrHs-a3SMZGTxZHwLoHz4

### Record ###

First, user clicks on a web page and the corresponding UI element is pushed into an array. If user clicks the element again, the UI element is removed from the array.

http://tellurium-users.googlegroups.com/web/TrUMPRecording.png?gda=TICFSkQAAACXZPxEX7Ki-M5C2JpeBoXXG2_DksVzaWnIiISCx_TXWm0M5UylZXdzBFXrfyb2GBFV6u9SiETdg0Q2ffAyHU-dzc4BZkLnSFWX59nr5BxGqA&gsc=yl4qswsAAABaguc98yIoD7duJ4WoO1up

### Generate ###

When the user clicks on the "Generate" button, TrUMP does the following two steps: first, TrUMP generates an internal tree to represent the UI elements using a grouping algorithm. During the tree generating procedure, extra nodes are generated to group UI elements together based on their corresponding location on the web DOM tree. The internal tree is very useful and holds all original data that can be used for customization.

Once the internal tree is built, TrUMP starts the second step, i.e., to build the default UI module. For each node in the internal tree, TrUMP generates a UI object based on its tag and if it is a parent node. There are some rules here,

  1. TrUMP uses a mapping rule to map the node to a UI object
  1. TrUMP uses a white list with preferred DOM attributes to construct the UI object
  1. Event handler attributes such as "onclick" are transferred into the respond attribute
  1. Group option is turned off by default

The detailed white list is shown in the following Javascript variable whileListAttributes and the event handler attribute list is in eventListAttributes.

```

var whiteListAttributes = ["id", "name", "value", "tag", "type", "class", "action", "method", "title", "text", "href", "src"]

var eventListAttributes = ["onclick", "ondoubleclick", "onkeyup", "onkeydown", "onkeypress", "onfocus", "onblur", "onmousedown", "onmouseover", "onmouseout", "onchange", "onsubmit", "onselect"]

```

With that, TrUMP creates the default UI module by ignoring a lot of attributes and these attributes will be listed as optional attributes for you to pick up in the "Customize" tab. The default UI module looks as follows,

http://tellurium-users.googlegroups.com/web/TrUMPOriginalSource.png?gda=iIgSZ0kAAACXZPxEX7Ki-M5C2JpeBoXXG2_DksVzaWnIiISCx_TXWiXI9uuDgpc6d16l1ETj4k62ZbY8FzpPR7dccZqdXNKLhAioEG5q2hncZWbpWmJ7IQ&gsc=yl4qswsAAABaguc98yIoD7duJ4WoO1up

### Customize ###

When user clicks on the "Customize" button, TrUMP will pull out the original data held in the internal tree and the current attributes utilized by the UI module to create the "Customize" view. When user clicks on an element, TrUMP lists all available optional attributes in the view as follows,

http://tellurium-users.googlegroups.com/web/CustomizeDownloadUI.png?gda=VlIDLkkAAACXZPxEX7Ki-M5C2JpeBoXXPO9eiOgUctaZvXxBMxZ6QTMbZM5wSMyxXHKfFXT4v2f6WZzO_TE9YttwbhictT3lhAioEG5q2hncZWbpWmJ7IQ&gsc=ULe1FQsAAADS90EDiskdDnLGwxfR4hma

User should choose set of attributes which will yield the fastest locator. These attributes should be selected using the following guidelines:
  1. If the UI element does not come with a red "X" mark, you can just leave it alone.
  1. Try to avoid attributes that may change. for example, CSS style and position.
  1. The header attribute includes relative xpath and try to avoid it unless it is necessary.

The attributes that user has chosen are used to build the composite locator, which is defined as follows,

```
class CompositeLocator {
    String header
    String tag
    String text
    String trailer //not used so far
    def position
    boolean direct
    Map<String, String> attributes = [:]
}
```

Note that the tag attribute cannot be changed and will not be on the optional attribute list. The direct attribute is used to indicate if the current node is a direct child of its parent or there are other nodes in between. This attribute is automatically detected by TrUMP and user will not see it on the optional attribute list either. The position attribute will be available only if the xpath for the UI element on the web comes with a position attribute.

The UI object consists of the following fields,
  * uid: should be unique for the same siblings in a UI module
  * namespace: for future extension, not used right now.
  * locator: the locator of the UI object, TrUMP uses a composite locator as shown above.
  * group: this option only applies to a collection type of UI object such as Container, Table, List, Form and so on. By default, this option is off and it can be turned on by checking the "group" attribute, i.e., group: "true". If it is on, the container type object will use its children's attributes to help locating its locator.
  * respond: define what JavaScript events the UI object could respond to.  This attribute is automatically generated by TrUMP and users do not have to change it.

After user clicks the "Save" button, the UI module is saved and the corresponding UI module source is updated automatically in the "Source" tab as follows,

http://tellurium-users.googlegroups.com/web/CustomizedUIModuleSource.png?gda=7zGZ904AAACXZPxEX7Ki-M5C2JpeBoXXA82FCWBYNCpX_2n25rbNalAlSwAoF2mY9vkB9iR3-cmX3AsZqbUllDyREqDLbjbX47Cl1bPl-23V2XOW7kn5sQ&gsc=bZgR_wsAAABkmVcjJdAtJbsdYoUTRcP2

After all red "X" marks are gone as shown in the following figure,

http://tellurium-users.googlegroups.com/web/TrUMPFinishCustomize.png?gda=_bWRIUoAAACXZPxEX7Ki-M5C2JpeBoXXG2_DksVzaWnIiISCx_TXWvMOW7QPA9SNoIY3uesfn7COiTpPuWhyHY3NqDGuPGPV_e3Wg0GnqfdKOwDqUih1tA&gsc=yl4qswsAAABaguc98yIoD7duJ4WoO1up

Congratulations, your UI module passes validations and you are ready to export the UI module into a Groovy file.

### Validation ###

TrUMP will try to validate the UI module automatically whenever you generate a new UI module or update it. TrUMP will evaluate each UI element's xpath the same way that Tellurium generates the runtime xpath from the UI module and check if the generated runtime xpath is unique in the current web page. If it is not unique, you will see a red "X" mark and you need to modify the element's attribute to make it disappear. If you dont see a red "X", you are done. You can export the generated UI module to a groovy file and start to write Tellurium tests based on the generated UI module.

### What are the Differences between Firefox 3 and Firefox 2 Versions ###

TrUMP has different xpi files for Firefox 2 and Firefox 3. There is no difference in the core functionality between the two versions. Firefox 3 version uses the XUL XML data source, which is only supported in Firefox 3, to display the UI module on the "Customize" tab and the attributes of the selected element. This data is dynamic and totally depends on the UIs that user recorded. XML data source is a more elegant way to build dynamic web. In the Firefox 2 version, we have to manually construct the same view by experience.