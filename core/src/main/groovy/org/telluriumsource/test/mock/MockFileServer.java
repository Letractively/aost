package org.telluriumsource.test.mock;

public class MockFileServer {

    private JettyFileServer server;

    public MockFileServer() {
        server = new JettyFileServer();
    }

    public MockFileServer(int port) {
        server = new JettyFileServer(port);
    }

    public MockFileServer(int port, String resourceBase) {
        server = new JettyFileServer(port, resourceBase);
    }

    public void start() {
        server.setDaemon(true);
        server.start();
    }

    public void stop() {
        server.shutDown();
    }
}
