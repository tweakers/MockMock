package com.mockmock.server;

import com.mockmock.AppStarter;
import com.mockmock.Settings;
import com.mockmock.http.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class HttpServer implements com.mockmock.server.Server
{
    private int port;

    private Settings settings;
    private IndexHandler indexHandler;
    private MailDetailHandler mailDetailHandler;
    private MailDetailHtmlHandler mailDetailHtmlHandler;
    private MailDeleteHandler mailDeleteHandler;
    private DeleteHandler deleteHandler;

    public void setPort(int port)
    {
        this.port = port;
    }

    public void start()
    {
        Server http = new Server(port);

        // get the path to the "static" folder. If it doesn't exists, check if it's in the folder of the file being executed.
        String path = "./static";
        if(settings.getStaticFolderPath() != null)
        {
            path = settings.getStaticFolderPath();
        }

        if( ! new File(path).exists())
        {
            System.out.println("Path to static folder does not exist: " + path);

            // get the directory we're in
            path = AppStarter.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = new File(path).getParent() + "/static";

            System.out.println("Using auto guessed folder: " + path);
        }

        System.out.println("Path to resources folder: " + path);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(path);

        Handler[] handlers = {
			this.indexHandler,
			this.mailDetailHandler,
			this.mailDetailHtmlHandler,
			this.mailDeleteHandler,
			this.deleteHandler,
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

    @Autowired
    public void setIndexHandler(IndexHandler indexHandler) {
        this.indexHandler = indexHandler;
    }

	@Autowired
	public void setMailDetailHandler(MailDetailHandler mailDetailHandler) {
		this.mailDetailHandler = mailDetailHandler;
	}

	@Autowired
	public void setMailDetailHtmlHandler(MailDetailHtmlHandler mailDetailHtmlHandler) {
		this.mailDetailHtmlHandler = mailDetailHtmlHandler;
	}

	@Autowired
	public void setMailDeleteHandler(MailDeleteHandler mailDeleteHandler) {
		this.mailDeleteHandler = mailDeleteHandler;
	}

    @Autowired
    public void setDeleteHandler(DeleteHandler deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
