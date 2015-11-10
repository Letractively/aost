

## Prerequisites ##

  * [Tellurium Core 0.7.0-SNAPSHOT](http://maven.kungfuters.org/content/repositories/snapshots/tellurium/tellurium-core/0.7.0-SNAPSHOT/)

## Motivation ##

Very often, some Tellurium users asked us to help them to track problems in their Tellurium test code. Due to some company policy, they cannot provide us the HTML source directly, but the UI module instead. Without the HTML source, there is no way for us to debug their test code because we do not have access to their web applications.

However, if we can do reverse engineering to generate the HTML source from the given UI module, we can use the [mock http server](http://code.google.com/p/aost/wiki/TelluriumMockHttpServer) to test the generated HTML Source without the need to access their web applications.

Driven by this motivation, we provided the following new method in `DslContext` for users to generate HTML source from UI modules:

```
  public String generateHtml(String uid)
```

## Implementation ##

The key is to generate the HTML source for each individual UI object from the composite locator, denoted by `clocator`. As a result, we added two methods to the `CompositeLocator` class:

```
class CompositeLocator {
    String header
    String tag
    String text
    String trailer
    Map<String, String> attributes = [:]

    public String generateHtml(boolean closeTag){ 
      ......
    }

    public String generateCloseTag(){
      ......
    }
```

where `generateHtml(boolean closeTag)` returns the generated HTML source from the composite locator and the boolean variable _closeTag_ indicates whether to generate the closing tag for the HTML source. For Container type UI objects, most likely, you will not generate the closing tag directly, but use the other method `generateCloseTag()` to generate the closing tag separately so that we can include its child elements in between.

Then on the base class UiObject, we add the `generateHtml()` method as follows,

```
abstract class UiObject implements Cloneable{
    String uid
    String namespace = null
  
    def locator

    //reference back to its parent
    def Container parent

    public String generateHtml(){
      if(this.locator != null){
        return getIndent() + this.locator.generateHtml(true) + "\n";
      }
      
      return "\n";
    }

    public String getIndent(){
      if(parent != null){
          return parent.getIndent() + "    ";
      }else{
        return "";
      }
    }
}
```

To make pretty print, we add a `getIndent()` method in the UiObject to get the indentation for the current UI object.

Once we added the `generateHtml()` method, all the concrete UI objects such as Button, InputBox, and UrlLink inherit this method to generate HTML source. However, for a Contain type, the implementation is different because we need to include its child UI objects in the HTML source. As a result, we overwrite the `generateHtml()` method in the UiObject.

```
class Container extends UiObject {
    def components = [:]

    @Override
    public String generateHtml(){
      StringBuffer sb = new StringBuffer(64);
      String indent = getIndent();

      if(this.components.size() > 0){
        if(this.locator != null)
          sb.append(indent + this.locator.generateHtml(false)).append("\n");
        this.components.each {String uid, UiObject obj ->
          sb.append(obj.generateHtml());
        }
        if(this.locator != null)
          sb.append(indent + this.locator.generateCloseTag()).append("\n");
      }else{
        if(this.locator != null){
          sb.append(this.locator.generateHtml(true)).append("\n")
        }
      }

      return sb.toString();
    }
}
```

UI templates in Tellurium objects such as List and Table make things more complicated. The basic idea is to elaborate all UI templates and key is to get the appropriate List size and Table size. We use an algorithm to determine the sizes and we don't want to go over the details here.

Finally, we add the `generateHtml(String uid)` method to the `DslContext` class

```
  public String generateHtml(String uid){
    WorkflowContext context = WorkflowContext.getContextByEnvironment(this.exploreJQuerySelector, this.exploreSelectorCache)
    def obj = walkToWithException(context, uid)
    return obj.generateHtml()
  }
```

Another method `generateHtml()` is used to generate the HTML source for all UI modules defined in a UI module class file.

```
  public String generateHtml(){
    StringBuffer sb = new StringBuffer(128)
    ui.registry.each {String key, UiObject val ->
      sb.append(val.generateHtml())
    }

    return sb.toString()
  }
```

## Usage ##

### Container ###

We used the following UI module

```
    ui.Form(uid: "accountEdit", clocator: [tag: "form", id: "editPage", method: "post"]) {
        InputBox(uid: "accountName", clocator: [tag: "input", type: "text", name: "acc2", id: "acc2"])
        InputBox(uid: "accountSite", clocator: [tag: "input", type: "text", name: "acc23", id: "acc23"])
        InputBox(uid: "accountRevenue", clocator: [tag: "input", type: "text", name: "acc8", id: "acc8"])
        TextBox(uid: "heading", clocator: [tag: "h2", text: "*Account Edit "])
        SubmitButton(uid: "save", clocator: [tag: "input", class: "btn", type: "submit", title: "Save", name: "save"])
    }
```

Call the `generateHtml()` method

```
    generateHtml("accountEdit");
```

and it generates the HTML source as follows,

```
<form id="editPage" method="post">
    <input type="text" name="acc2" id="acc2"/>
    <input type="text" name="acc23" id="acc23"/>
    <input type="text" name="acc8" id="acc8"/>
    <h2>Account Edit </h2>
    <input class="btn" type="submit" title="Save" name="save"/>
</form>
```

### List ###

We have the following UI module with a List, which defines a set of URL links using UI template,

```
    ui.Container(uid: "subnav", clocator: [tag: "ul", id: "subnav"]) {
        Container(uid: "CoreLinks", clocator: [tag: "li", id: "core_links"]) {
          List(uid: "links", clocator: [tag: "ul"], separator: "li") {
            UrlLink(uid: "all", clocator: [:])
          }
        }
        UrlLink(uid: "subscribe", clocator: [tag: "li", id: "subscribe"])
    }
```

The generated HTML source is

```
<ul id="subnav">
    <li id="core_links">
        <ul>
          <li>
            <a/>
          </li>
        </ul>
    </li>
    <li id="subscribe"/>
</ul>
```

### Table ###

Table is a frequently used UI object with UI templates, the Tellurium Issue web page has a data grid to show the issues and it can described using the following UI module:

```
    ui.Table(uid: "issueResult", clocator: [id: "resultstable", class: "results"], group: "true") {
        TextBox(uid: "header: 1", clocator: [:])
        UrlLink(uid: "header: 2", clocator: [text: "*ID"])
        UrlLink(uid: "header: 3", clocator: [text: "*Type"])
        UrlLink(uid: "header: 4", clocator: [text: "*Status"])
        UrlLink(uid: "header: 5", clocator: [text: "*Priority"])
        UrlLink(uid: "header: 6", clocator: [text: "*Milestone"])
        UrlLink(uid: "header: 7", clocator: [text: "*Owner"])
        UrlLink(uid: "header: 9", clocator: [text: "*Summary + Labels"])
        UrlLink(uid: "header: 10", clocator: [text: "*..."])

        //define table elements
        //for the border column
        TextBox(uid: "row: *, column: 1", clocator: [:])
        TextBox(uid: "row: *, column: 8", clocator: [:])
        TextBox(uid: "row: *, column: 10", clocator: [:])
        //For the rest, just UrlLink
        UrlLink(uid: "all", clocator: [:])
    }
```

The generated HTML source is as follows

```
<table id="resultstable" class="results">
 <tbody>
  <tr>
   <th>
   

   </th>
   <th>
    <a>ID</a>

   </th>
   <th>
    <a>Type</a>

   </th>
   <th>
    <a>Status</a>

   </th>
   <th>
    <a>Priority</a>

   </th>
   <th>
    <a>Milestone</a>

   </th>
   <th>
    <a>Owner</a>

   </th>
   <th>


   </th>
   <th>
    <a>Summary + Labels</a>

   </th>
   <th>
    <a>...</a>

   </th>
  </tr>
  <tr>
   <td>
   

   </td>
   <td>
    <a/>

   </td>
   <td>
    <a/>

   </td>
   <td>
    <a/>

   </td>
   <td>
    <a/>

   </td>
   <td>
    <a/>

   </td>
   <td>
    <a/>

   </td>
   <td>
   

   </td>
   <td>
    <a/>

   </td>
   <td>
   

   </td>
   <td>
    <a/>

   </td>
  </tr>
 </tbody>
</table>
```

## Summary ##

The `generateHtml(uid)` method is really helpful if you want to help other people to track the problem in their Tellurium test code but you have not access to their web applications and HTML sources. Once the HTML source is generated, you can use the [mock http server](http://code.google.com/p/aost/wiki/TelluriumMockHttpServer) to test the generated HTML Source].

## Resources ##

  * [Tellurium Mock Http Server](http://code.google.com/p/aost/wiki/TelluriumMockHttpServer)
  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide)