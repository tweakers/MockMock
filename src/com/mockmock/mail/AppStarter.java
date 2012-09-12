package com.mockmock.mail;

import org.apache.commons.cli.*;
import org.subethamail.smtp.server.SMTPServer;

public class AppStarter
{
    /**
     * The default port where MockMock will run on
     */
    private static int serverPort = 25000;


    public static void main(String[] args)
    {
        // get the parameters that are allowed
        parseOptions(args);

        // this handles every message that is received
        MockMockMessageHandlerFactory handlerFactory = new MockMockMessageHandlerFactory();

        // start the smtp server!
        SMTPServer server = new SMTPServer(handlerFactory);
        server.setPort(serverPort);

        try
        {
            System.out.println("Starting MockMock on port " + serverPort);
            server.start();
        }
        catch (Exception e)
        {
            System.err.println("Could not start MockMock. Maybe port " + serverPort + " is already in use?");
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
        options.addOption("p", true, "The port number to use");
        options.addOption("?", false, "Shows this help information");

        // parse the given arguments
        CommandLineParser parser = new PosixParser();

        try
        {
            CommandLine cmd = parser.parse( options, args);

            if(cmd.hasOption("?"))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "java -jar MockMock.jar -p 25000", options );
                System.exit(0);
            }
            else
            {
                if(cmd.hasOption("p"))
                {
                    try
                    {
                        serverPort = Integer.valueOf(cmd.getOptionValue("p"));
                    }
                    catch (NumberFormatException e)
                    {
                        System.err.println("Invalid port number given, using default " + serverPort);
                    }
                }
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}