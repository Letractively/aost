

## Prerequisites ##

  * [Tellurium Core 0.7.0-SNAPSHOT](http://maven.kungfuters.org/content/repositories/snapshots/tellurium/tellurium-core/0.7.0-SNAPSHOT/)
  * Java 6

## Motivation ##

From time to time, some Tellurium users ask us to help them with their tests. The dilemma is that we do not have the access to their web applications but just a piece of HTML source provided by our user. Thus, it is really difficult for us to debug their tests and to figure out what's wrong there. One possible way is to manually create a HTML page based on the HTML source they provided us and then use a web server such as Jetty to render the HTML for us. But this requires some extra work and need to run a web server instance.

Java 6 provides a built-in Http server, which we can leverage and render the users' HTML sources. The Java 6 http server can be used to build an embedded http server to automatically render whatever HTML source we provide to it. Since the http server is an embedded one, it is very easy to configure, start, and stop it programmatically, for example, we can put the start procedure in the method annotated with @BeforeClass and put the stop procedure in the method annotated with @AfterClass in JUnit or TestNG.

Another advantage to use an embedded http server is that we can test some special web content without creating a Web application. For example, if we want to test some new methods for the Tellurium List object, we can simply create a HTML source and use the embedded http server to render it.

## Tellurium Mock Http Server ##

Tellurium mock http server is designed to achieve the goals specified in the above motivation section. Tellurium defined two classes, i.e.,  MockHttpHandler and MockHttpServer.

### Mock Http Handler ###

The MockHttpHandler is a class to process the http request,

```
public class MockHttpHandler implements HttpHandler {

  private Map<String, String> contents = new HashMap<String, String>();

  private String contentType = "text/html";

  public void handle(HttpExchange exchange) {
     ......
  }
}
```

The main method in MockHttpHandler is `handle(HttpExchange exchange)`, which reads the request URI, finds the corresponding response HTML source from the hash map _contents_, and then sends it back to the http client.

By default, the response will be treated as a HTML source, you can change this by using the following setter.

```
public void setContentType(String contentType)
```

MockHttpHandler includes two methods to add URI and its HTML source to the hash map _contents_, i.e.,

```
public void registerBody(String url, String body)

public void registerHtml(String url, String html)
```

The MockHttpHandler comes with a default HTML template as follows,

```
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Mock HTTP Server</title>
    </head>
    <body>
    BODY_HTML_SOURCE   
    </body>
</html>
```

If you use `registerBody(String url, String body)`, the MockHttpHandler will use the above HTML template to wrap the HTML body. You overwrite the default HTML template by calling `registerHtml(String url, String html)` directly, which will use the whole HTML source you provide in the variable _html_.

Usually, the MockHttpHandler is encapsulated by the MockHttpServer and you don't really need to work on it directly.

### Mock Http Server ###

The MockHttpServer includes an embedded http server, a http handler, and a http port:

```
public class MockHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private MockHttpHandler handler;

  public MockHttpServer() {
    this.handler = new MockHttpHandler();
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port) {
    this.handler = new MockHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public MockHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  ......
}
```

As you can see, the MockHttpServer provides three different constructors so that you can overwrite the default values. The MockHttpServer encapsulates the MockHttpHander by providing the following methods,

```
public void setContentType(String contentType)

public void registerHtmlBody(String url, String body)

public void registerHtml(String url, String html)
```

You can stop and start the server with the following methods,

```
public void start()

public void stop()
```

## How to Use ? ##

### UI Module ###

We use a modified version of a HTML source provided by one Tellurium user as an example and create the UI module Groovy class as follows

```
public class ListModule extends DslContext {
  public static String LIST_BODY = """
<div class="thumbnails">
    <ul>
        <li class="thumbnail">
            <img alt="Image 1"
                 src="/images_root/image_pictures/01.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 2"
                 src="/images_root/image_pictures/02.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Image 3"
                 src="/images_root/image_pictures/03.jpg"/>
        </li>
        <li class="thumbnail">
        </li>
        <li class="thumbnail active">
            <img alt="Image 4"
                 src="/images_root/image_pictures/04.jpg"/>
        </li>
        <li class="thumbnail potd">
            <div class="potd-icon png-fix"/>
            <img alt="Image 5"
                 src="/images_root/image_pictures/05.jpg"/>
        </li>
    </ul>
</div>    
  """

  public void defineUi() {
    ui.Container(uid: "rotator", clocator: [tag: "div", class: "thumbnails"]) {
      List(uid: "tnails", clocator: [tag: "ul"], separator: "li") {
        UrlLink(uid: "all", clocator: [:])
      }
    }
  }
}
```

The reason we include the HTML source in a Groovy file is that the """ quote in Groovy is very easy to present complicated HTML source as a String variable. In Java, you have to concatenate each line of the HTML Source to make it a String variable.

The `defineUi()` defines the UI module for the given HTML source. The major part of the UI module is a List, which uses UI templates to represent a list of links. You can see how easy and concise to use UI templates to represent UI elements in Tellurium.

### Tellurium Test Case ###

Based on the ListModule UI module, we define a Tellurium JUnit test case as follows,

```
public class ListTestCase  extends TelluriumJavaTestCase {
    private static MockHttpServer server;

    @BeforeClass
    public static void setUp(){
        server = new MockHttpServer(8080);
        server.registerHtmlBody("/list.html", ListModule.LIST_BODY);
        server.start();
    }

    @Test
    public void testGetSeparatorAttribute(){
        ListModule lm = new ListModule();
        lm.defineUi();

        connectUrl("http://localhost:8080/list.html");

        lm.disableJQuerySelector();
        String attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        assertEquals("thumbnail potd", attr);

        lm.useJQuerySelector();
        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        assertEquals("thumbnail potd", attr);
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();    
    }
}
```

In the `setUp()` method, we create a MockHttpServer on port 8080,

```
server = new MockHttpServer(8080);
```

register the HTML body with the URI "/list.html"

```
server.registerHtmlBody("/list.html", ListModule.LIST_BODY);
```

and then start the server

```
server.start();
```

In the `tearDown()` method, we stop the server.

```
server.stop(); 
```

The actual test case is exactly the same as a regular Tellurium test case and the only difference is that the http URL points to localhost.

First, we create a UI module instance,

```
  ListModule lm = new ListModule();
  lm.defineUi();
```

Then, we connect to the MockHttpServer

```
  connectUrl("http://localhost:8080/list.html");
```

The following test code use xpath locator to test the class attribute for the separator "li".

```
  lm.disableJQuerySelector();
  String attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
  assertEquals("thumbnail potd", attr);
```

Similar to the above test code, the following code use jQuery selector to test the class attribute for the separator "li".

```
  lm.useJQuerySelector();
  attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
  assertEquals("thumbnail potd", attr);
```

## Summary ##

The MockHttpServer is an embedded http server and it is very convenient to use. However, it is designed to test simple HTML source and you should use a real web server if you need to render very complicated web content.

## Resources ##

  * [HttpServer Java Doc](http://java.sun.com/javase/6/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html)
  * [Tellurium User Guide](http://code.google.com/p/aost/wiki/UserGuide?tm=6)