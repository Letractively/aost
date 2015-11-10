

# Introduction #

[QUnit](http://docs.jquery.com/QUnit) is a powerful, easy-to-use, JavaScript test suite. It can be used to test jQuery code and jQuery plugins as well as any generic JavaScript code. Like [JsUnit](http://www.jsunit.net/) or any other javascript testing frameworks, QUnit needs to be run in a web browser. How good it is if we could run QUnit from Java code directly and don't need to worry about how to construct a web page and load it up with a browser?

Here we introduce one way to implement the above idea. The basic idea is to use [Java 6 http server](http://java.sun.com/javase/6/docs/jre/api/net/httpserver/spec/index.html) to run a web page created by Java code. A web page is created dynamically using Java to include QUnit tests and [TelluriumJunitTestCase](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Testing_Support) is extended to automatically open the web page in a web browser.

# Prerequisites #

  * [Tellurium Core 0.7.0-SNAPSHOT](http://maven.kungfuters.org/content/repositories/snapshots/org/telluriumsource/tellurium-core/0.7.0-SNAPSHOT/)
  * Java 6
  * JUnit 4
  * Groovy 1.6 (optional)

# Implementation #

## QUnit Http Server ##

QUnitHttpServer is used as a web server, but it is a very lightweight one. One Java class can serve this purpose.

```
package org.telluriumsource.tester;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler

public class QUnitHttpServer {
  //default port
  private int port = 8080;

  private HttpServer server = null;
  private QUnitHttpHandler handler;

  public QUnitHttpServer() {
    this.handler = new QUnitHttpHandler();
    this.server = HttpServer.create();
  }

  public QUnitHttpServer(int port) {
    this.handler = new QUnitHttpHandler();
    this.port = port;
    this.server = HttpServer.create();
  }

  public QUnitHttpServer(int port, HttpHandler handler) {
    this.port = port;
    this.handler = handler;
    this.server = HttpServer.create();
  }

  public void setContentType(String contentType){
    this.handler.setContentType(contentType)
  }

  public void registerHtmlBody(String url, String javascript, String body){
    this.server.createContext(url, this.handler);
    this.handler.registerTest(url, javascript, body);
  }

  public void setServerPort(int port){
    this.port = port;
  }

  public void start() {
    server.bind(new InetSocketAddress(this.port), 0);
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  public void stop(){
    if(server != null)
      server.stop(50);
  }
}

```

## QUnit Http Handler ##

QUnitHttpHandler is used to create the web page that includes QUnit tests. We use a Groovy class to implement it so that we can use the """ quotation in Groovy. You don't really need to use Groovy and can use String concatenation for the same purpose.

```
package org.telluriumsource.tester;

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.Headers

public class QUnitHttpHandler implements HttpHandler {

  private static String HEADER1 = """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
          <title>QUnit Tester</title>
          <script src="http://code.jquery.com/jquery-1.3.2.js"> </script>
          <link rel="stylesheet" href="http://github.com/jquery/qunit/raw/master/qunit/qunit.css" type="text/css" media="screen" />
          <script type="text/javascript" src="http://github.com/jquery/qunit/raw/master/qunit/qunit.js"></script>
          <script type="text/javascript">
            \$(document).ready(function(){
"""
  private static String HEADER2 = """
            });          
          </script>
        </head>
        <body>
  """

  private static String TRAILER = """
    </body>
    </html>
  """

  private static String ERROR_MESSAGE = """
    <div>Cannot find HTML content</div>
  """
  private static String CONTENT_TYPE = "Content-Type";

  private Map<String, String> contents = new HashMap<String, String>();

  private String contentType = "text/html";

  public void setContentType(String contentType){
    this.contentType = contentType;
  }

  public void registerTest(String url, String javascript, String body){
    if(body != null)
      this.contents.put(url, HEADER1 + javascript + HEADER2 + body + TRAILER);
    else
      this.contents.put(url, HEADER1 + javascript + HEADER2 + TRAILER);
  }

  public void setTrailer(String trailer){
    this.TRAILER = trailer;
  }

  public void setBody(String body){
    this.body = body;
  }

  public void handle(HttpExchange exchange) {
    String requestMethod = exchange.getRequestMethod();

    if (requestMethod.equalsIgnoreCase("GET") || requestMethod.equalsIgnoreCase("POST")) {
      Headers responseHeaders = exchange.getResponseHeaders();
      responseHeaders.set(CONTENT_TYPE, this.contentType);
      exchange.sendResponseHeaders(200, 0);

      OutputStream responseBody = exchange.getResponseBody();

      String uri = exchange.getRequestURI();
      String html = this.contents.get(uri);
      if(html == null){
        html = HEADER1 + HEADER2 + ERROR_MESSAGE + TRAILER;
      }

      responseBody.write(html.getBytes());

      responseBody.close();
    }
  }
}

```

## QUnit Test Case ##

QUnit Test Case extends TelluriumJUnitTestCase to start QUnit http server, dynamically create the web page that includes the QUnit tests, and launch the web browser.


```
package org.telluriumsource.tester;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

public class QUnitTestCase extends TelluriumJUnitTestCase {
    private static QUnitHttpServer server;
    //launch Firefox browser in a multiple-window mode
    static{
        setCustomConfig(true, 4444, "*chrome", true, null);
    }

    @BeforeClass
    public static void setUp(){
        server = new QUnitHttpServer(8080);
        server.start();
        connectSeleniumServer();
    }
    
    @AfterClass
    public static void tearDown(){
        if(server != null)
          server.stop();
    }

    public static void registerTest(String testname, String javascript, String body){
        server.registerHtmlBody("/" + testname + ".html", javascript, body);
    }

    public static void runTest(String testname){
        connectUrl("http://localhost:8080/" + testname + ".html");    
    }
}
```

That is all the QUnit support code.

# Example #

We use the demo on the [QUnit](http://docs.jquery.com/QUnit) page to show you how to run QUnit tests from Java.

First, create a Groovy class to hold all the QUnit tests and the html body. Again, you can use Java String concatenation for the same purpose.

```
package org.telluriumsource.test;

class Demo {

  public static String js = """
    test("a basic test example", function() {
      ok( true, "this test is fine" );
      var value = "hello";
      equals( "hello", value, "We expect value to be hello" );
    });

    module("Module A");

    test("first test within module", function() {
      ok( true, "all pass" );
    });

    test("second test within module", function() {
      ok( true, "all pass" );
    });

    module("Module B");

    test("some other test", function() {
      expect(2);
      equals( true, false, "failing test" );
      equals( true, true, "passing test" );
    });
  """

  public static String body = """
     <h1 id="qunit-header">QUnit example</h1>
     <h2 id="qunit-banner"></h2>
     <h2 id="qunit-userAgent"></h2>
     <ol id="qunit-tests"></ol>
  """
}
```

The the test case is just couple lines.

```
package org.telluriumsource.test;

import org.telluriumsource.tester.QUnitTestCase;
import org.junit.Test;

public class QUnitDemoTest extends QUnitTestCase {

    @Test
    public void testDemo(){
        registerTest("demo",Demo.js, Demo.body);
        runTest("demo");
    }
}
```

Run the test case, you will see the test results as follows.


http://tellurium-users.googlegroups.com/web/QUnitTestResult.png?gda=2qABBkUAAACsdq5EJRKLPm_KNrr_VHB_7oey9JbGi19AQSckTgSRXZ-XXUeqs76EOP7DaVxZw6hzlqnWZQD3y6jZqCMfSFQ6Gu1iLHeqhw4ZZRj3RjJ_-A&gsc=iB9gogsAAAAY3ihZvFwqqrOrtBcsjfa3

# Improvement #

We can do a bit improvement so that the QUnit tester can load up the QUnit test script and the corresponding HTML body from a local disk. The advantage is that you don't need to recompile the code whenever you change the Javascript and html files. This can be easily achieved by adding the following two methods to QUnitHttpServer.

```
  public String getJsFile(String file){
    return new File(ClassLoader.getSystemResource("org/telluriumsource/js/${file}.js").getFile()).text;
  }

  public String getHtmlFile(String file){
    return new File(ClassLoader.getSystemResource("org/telluriumsource/html/${file}.html").getFile()).text;
  }
}
```

Here we assumed the name conversion that all the QUnit test scripts come with the ".js" suffix in the "js" subdirectory and html body files are under the "html" subdirectory with the ".html" suffix. Furthermore, the file names are the same as the test name.

Then, in class QUnitTestCase we can have a new method as follows,

```
    public static void registerTest(String testname){
       server.registerHtmlBody("/" + testname + ".html", server.getJsFile(testname), server.getHtmlFile(testname));
    }
```

After that, the Demo test case can be simplified as follows.

```
    @Test
    public void testDemo(){
        registerTest("demo");
        runTest("demo");
    }
```

For the sample code, please check them from [Tellurium Engine](http://aost.googlecode.com/svn/trunk/engine/src/test) project.

# Further Work #

You can change the above code for the following purposes:

  * Change the QUnitHttpHandler to a Java class. The only purpose we use Groovy is because Groovy supports the """ quotation. You can achieve the same goal by String concatenation.
  * Even use Java code to launch a web browser directly instead of relying on TelluriumJUnitTestCase.
  * Use the same mechanism to run JsUnit tests from Java.

# Resources #

  * [QUnit](http://docs.jquery.com/QUnit)
  * [JsUnit](http://www.jsunit.net/)
  * [Tellurium Test Support](http://code.google.com/p/aost/wiki/UserGuide070TelluriumBasics#Testing_Support)
  * [Java 6 Http Server](http://java.sun.com/javase/6/docs/jre/api/net/httpserver/spec/index.html)