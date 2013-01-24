package com.mockmock;

import org.apache.commons.cli.*;

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

        Server smtpServer = new SmtpServer(smtpPort);
        smtpServer.start();

        Server httpServer = new HttpServer(httpPort);
        httpServer.start();
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

            parseSmtpPortOption(cmd);
            parseHttpPortOption(cmd);
            parseMailQueueSizeOption(cmd);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    protected static void parseSmtpPortOption(CommandLine cmd)
    {
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
    }

    protected static void parseHttpPortOption(CommandLine cmd)
    {
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
    }

    protected static void parseMailQueueSizeOption(CommandLine cmd)
    {
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
}