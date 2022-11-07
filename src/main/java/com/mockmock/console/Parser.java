package com.mockmock.console;

import com.mockmock.Settings;
import org.apache.commons.cli.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class Parser
{
    /**
     * Parses the given parameters and returns the possible options
     * @param args String[]
     */
    public Settings parseOptions(String[] args, Settings settings)
    {
        // define the possible options
        Options options = new Options();
        options.addOption("p", "smtp", true, "The mail port number to use. Default is 25.");
        options.addOption("h", "http", true, "The http port number to use. Default is 8282.");
        options.addOption("m", "queue", true, "The maximum size of the mail queue. Default is 1000.");
        options.addOption("c", "channels", true, "Comma separated list of channels. Default is #postman.");
        options.addOption("f", "filter-from", true, "Filters out from email addresses (comma separated).");
        options.addOption("t", "filter-to", true, "Filters out to email addresses (comma separated).");
        options.addOption("s", "static", true, "Full path to the folder containing the static files like images and css.");
        options.addOption("ec", "console", false, "Turns on emails printing to console. Default off");
        options.addOption("?", "help", false, "Shows this help information.");

        // parse the given arguments
        CommandLineParser parser = new PosixParser();

        try
        {
            CommandLine cmd = parser.parse(options, args);

            if(cmd.hasOption("?"))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp( "java -jar MockMock.jar -p 25 -h 8282", options );
                System.exit(0);
            }

            parseShowEmailInConsoleOption(cmd, settings);
            parseSmtpPortOption(cmd, settings);
            parseHttpPortOption(cmd, settings);
            parseMailQueueSizeOption(cmd, settings);
			parseFilterFromEmailAddressesOption(cmd, settings);
			parseFilterToEmailAddressesOption(cmd, settings);
            parseStaticFolderOption(cmd, settings);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return settings;
    }

    protected void parseShowEmailInConsoleOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("ec"))
        {
            settings.setShowEmailInConsole(true);
        }
    }

    protected void parseSmtpPortOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("p"))
        {
            try
            {
                settings.setSmtpPort(Integer.parseInt(cmd.getOptionValue("p")));
            }
            catch (NumberFormatException e)
            {
                System.err.println("Invalid mail port given, using default " + settings.getSmtpPort());
            }
        }
    }

    protected void parseHttpPortOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("h"))
        {
            try
            {
                settings.setHttpPort(Integer.parseInt(cmd.getOptionValue("h")));
            }
            catch (NumberFormatException e)
            {
                System.err.println("Invalid http port given, using default " + settings.getHttpPort());
            }
        }
    }

    protected void parseMailQueueSizeOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("m"))
        {
            try
            {
                settings.setMaxMailQueueSize(Integer.parseInt(cmd.getOptionValue("m")));
            }
            catch (NumberFormatException e)
            {
                System.err.println("Invalid max mail queue size given, using default " + settings.getMaxMailQueueSize());
            }
        }
    }

	protected void parseFilterFromEmailAddressesOption(CommandLine cmd, Settings settings)
	{
		if(cmd.hasOption("f"))
		{
			String input = cmd.getOptionValue("f");
			String[] emailAddresses = input.split(",");
			settings.setFilterFromEmailAddresses(new HashSet<>(Arrays.asList(emailAddresses)));
		}
	}

	protected void parseFilterToEmailAddressesOption(CommandLine cmd, Settings settings)
	{
		if(cmd.hasOption("t"))
		{
			String input = cmd.getOptionValue("t");
			String[] emailAddresses = input.split(",");
			settings.setFilterToEmailAddresses(new HashSet<>(Arrays.asList(emailAddresses)));
		}
	}

    protected void parseStaticFolderOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("s"))
        {
            settings.setStaticFolderPath(cmd.getOptionValue("s"));
        }
    }
}
