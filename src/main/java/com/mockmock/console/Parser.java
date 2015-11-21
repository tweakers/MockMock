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
        options.addOption("p", true, "The mail port number to use. Default is 25000.");
        options.addOption("h", true, "The http port number to use. Default is 8282.");
        options.addOption("m", true, "The maximum size of the mail qeueue. Default is 1000.");
        options.addOption("c", true, "Comma separated list of channels. Default is #postman.");
        options.addOption("ff", true, "Filters out from email addresses (comma separated).");
        options.addOption("ft", true, "Filters out to email addresses (comma separated).");
        options.addOption("s", true, "Full path to the folder containing the static files like images and css.");
        options.addOption("ec", false, "Turns on emails printing to console. Default off");
        options.addOption("?", false, "Shows this help information.");

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

            partseShowEmailInConsoleOption(cmd, settings);
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

    protected void partseShowEmailInConsoleOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("ec"))
        {
            try
            {
                // settings.setShowEmailInConsole(Boolean.valueOf(cmd.getOptionValue("ec")));
                settings.setShowEmailInConsole(true);
            }
            catch(IllegalArgumentException e)
            {
                System.err.println("Invalid value given, using default " + settings.getShowEmailInConsole());
            }
        }
    }

    protected void parseSmtpPortOption(CommandLine cmd, Settings settings)
    {
        if(cmd.hasOption("p"))
        {
            try
            {
                settings.setSmtpPort(Integer.valueOf(cmd.getOptionValue("p")));
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
                settings.setHttpPort(Integer.valueOf(cmd.getOptionValue("h")));
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
                settings.setMaxMailQueueSize(Integer.valueOf(cmd.getOptionValue("m")));
            }
            catch (NumberFormatException e)
            {
                System.err.println("Invalid max mail queue size given, using default " + settings.getMaxMailQueueSize());
            }
        }
    }

	protected void parseFilterFromEmailAddressesOption(CommandLine cmd, Settings settings)
	{
		if(cmd.hasOption("ff"))
		{
			String input = cmd.getOptionValue("ff");
			String[] emailAddresses = input.split(",");
			settings.setFilterFromEmailAddresses(new HashSet<>(Arrays.asList(emailAddresses)));
		}
	}

	protected void parseFilterToEmailAddressesOption(CommandLine cmd, Settings settings)
	{
		if(cmd.hasOption("ft"))
		{
			String input = cmd.getOptionValue("ft");
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
