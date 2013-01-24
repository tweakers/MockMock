package com.mockmock.mail;

import com.mockmock.http.DeleteHandler;
import com.mockmock.http.IndexHandler;
import com.mockmock.http.MailDetailHandler;
import org.apache.commons.cli.*;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.subethamail.smtp.server.SMTPServer;

import java.io.File;

public class AppStarter
{
    public static final float VERSION_NUMBER = 1.0f;

    /**
     * The default port where MockMock will run on
     */
    private static int smtpPort = 25000;

    /**
     * The default port for the http server
     */
    private static int httpPort = 8282;

    /**
     * The maximum size the mail queue may be.
     */
    public static int maxMailQueueSize = 1000;


    public static void main(String[] args)
    {
        // get the parameters that are allowed
        parseOptions(args);

        // this handles every message that is received
        MockMockMessageHandlerFactory handlerFactory = new MockMockMessageHandlerFactory();

        // start the smtp server!
        SMTPServer server = new SMTPServer(handlerFactory);
        server.setSoftwareName("MockMock SMTP Server version " + VERSION_NUMBER);
        server.setPort(smtpPort);

        try
        {
            System.out.println("Starting MockMock on port " + smtpPort);
            server.start();
        }
        catch (Exception e)
        {
            System.err.println("Could not start MockMock. Maybe port " + smtpPort + " is already in use?");
        }

        Server http = new Server(httpPort);

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
            System.out.println("Starting http server on port " + httpPort);
            http.start();
            http.join();
        }
        catch (Exception e)
        {
            System.err.println("Could not start http server. Maybe port " + httpPort + " is already in use?");
        }
    }

    /**
     * Parses the given parameters and returns the possible options
     * @param args String[]
     */
    public static void parseOptions(String[] args)
    {
        // define the possible options
        Options options = new Options();
        options.addOption("p", true, "The mail port number to use. Default is 25000.");
        options.addOption("h", true, "The http port number to use. Default is 8282.");
        options.addOption("m", true, "The maximum size of the mail qeueue. Default is 1000.");
        options.addOption("?", false, "Shows this help information");

        // parse the given arguments
        CommandLineParser parser = new PosixParser();

        try
        {
            CommandLine cmd = parser.parse(options, args);

            if(cmd.hasOption("?"))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "java -jar MockMock.jar -p 25000 -h 8282", options );
                System.exit(0);
            }

            if(cmd.hasOption("p"))
            {
                try
                {
                    smtpPort = Integer.valueOf(cmd.getOptionValue("p"));
                }
                catch (NumberFormatException e)
                {
                    System.err.println("Invalid mail port given, using default " + smtpPort);
                }
            }

            if(cmd.hasOption("h"))
            {
                try
                {
                    httpPort = Integer.valueOf(cmd.getOptionValue("h"));
                }
                catch (NumberFormatException e)
                {
                    System.err.println("Invalid http port given, using default " + httpPort);
                }
            }

            if(cmd.hasOption("m"))
            {
                try
                {
                    maxMailQueueSize = Integer.valueOf(cmd.getOptionValue("m"));
                }
                catch (NumberFormatException e)
                {
                    System.err.println("Invalid max mail queue size given, using default " + maxMailQueueSize);
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}