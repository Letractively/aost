package org.tellurium.test.mock

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class MockHttpServer {

  private HttpServer server = null;

  public void start(int port, String url, HttpHandler handler) {
    server = HttpServer.create(new InetSocketAddress(port), 0);
    server.createContext(url, handler);
    server.setExecutor(null); // creates a default executor
    server.start();
  }

  public void stop(){
    if(server != null)
      server.stop();
  }

}