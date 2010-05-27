package org.telluriumsource.test.mock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MockFileServer {

    private JettyFileServer server;

//    private ExecutorService executor;

    public MockFileServer() {
//        this.executor = Executors.newFixedThreadPool(1);
        server = new JettyFileServer();
    }

    public MockFileServer(int port) {
//        this.executor = Executors.newFixedThreadPool(1);
        server = new JettyFileServer(port);
    }

    public MockFileServer(int port, String resourceBase) {
//        this.executor = Executors.newFixedThreadPool(1);
        server = new JettyFileServer(port, resourceBase);
    }
    
    public void start(){
//        executor.execute((Runnable)server);
         server.setDaemon(true);
         server.start();
   }
    
    public void stop(){
        server.shutDown();
    }
}
