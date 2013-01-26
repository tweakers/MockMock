package com.mockmock;

import com.mockmock.http.DeleteHandler;
import com.mockmock.http.IndexHandler;
import com.mockmock.http.MailDetailHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.io.File;

public class HttpServer implements com.mockmock.Server
{
    private int port;

    public HttpServer(int port)
    {
        this.port = port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void start()
    {
        Server http = new Server(port);

        // get the path to the "static" folder. If it doesn't exists, check if it's in the folder of the file being
        // executed
        String path = "./static";
        if(!new File(path).exists())
        {
            // get the directory we're in
            path = AppStarter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = new File(path).getParent() + "/static";
        }

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(path);

        Handler[] handlers = {
                new IndexHandler(),
                new MailDetailHandler(),
                new DeleteHandler(),
            resourceHandler
        };
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(handlers);
        http.setHandler(handlerList);

        try
        {
            System.out.println("Starting http server on port " + port);
            http.start();
            http.join();
        }
        catch (Exception e)
        {
            System.err.println("Could not start http server. Maybe port " + port + " is already in use?");
        }
    }
}
