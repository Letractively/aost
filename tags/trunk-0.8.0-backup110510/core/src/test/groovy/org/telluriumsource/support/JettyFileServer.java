package org.telluriumsource.support;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *  Consider to move this class to a support sub-project/module.
 *  We really don't need a jetty dependency in Tellurium Core
 *
 *         Date: May 27, 2010
 */
public class JettyFileServer extends Thread {

    private int port;

    private String resourceBase;

    private Server server;

    private boolean isRunning = false;
    
    public JettyFileServer(){
        this.port = 8088;
        this.resourceBase = ".";
    }

    public JettyFileServer(int port) {
        this.port = port;
        this.resourceBase = ".";
    }

    public JettyFileServer(int port, String resourceBase) {
        this.port = port;
        this.resourceBase = resourceBase;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getResourceBase() {
        return resourceBase;
    }

    public void setResourceBase(String resourceBase) {
        this.resourceBase = resourceBase;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void start()  {
        server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        server.addConnector(connector);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });

        resource_handler.setResourceBase(resourceBase);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
        server.setHandler(handlers);
        try {
            server.start();
            isRunning = true;
//            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            isRunning = false;
        }
    }

    public void shutDown() {
        if(server != null){
            try {
                server.stop();
            } catch (Exception e) {

            }
            isRunning = false;
        }
    }

    public void run() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
            isRunning = false;
        }
    }
}
