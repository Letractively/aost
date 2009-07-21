package org.tellurium.test.mock

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class MockHttpHandler implements HttpHandler {
  private String HEADER = """
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
        <html>
        <head>
          <title>Mock HTTP Server</title>
        </head>
        <body>
  """

  private String TRAILER = """
    </body>
    </html>
  """

  private String body;

  public void setHeader(String header) {
    this.HEADER = header;
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
      responseHeaders.set("Content-Type", "text/html");
      exchange.sendResponseHeaders(200, 0);

      OutputStream responseBody = exchange.getResponseBody();
/*
      Headers requestHeaders = exchange.getRequestHeaders();
      Set<String> keySet = requestHeaders.keySet();
      Iterator<String> iter = keySet.iterator();
      while (iter.hasNext()) {
        String key = iter.next();
        List values = requestHeaders.get(key);
        String s = key + " = " + values.toString() + "\n";
        responseBody.write(s.getBytes());
      }
      */
      responseBody.write(this.HEADER.getBytes());
      if(this.body != null)
        responseBody.write(this.body.getBytes());
      responseBody.write(this.TRAILER.getBytes());
      responseBody.close();
    }
  }
}