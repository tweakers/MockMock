package com.mockmock;

import com.google.common.eventbus.EventBus;
import com.mockmock.mail.MailQueue;
import org.apache.commons.cli.*;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class AppStarter
{
    public static final float VERSION_NUMBER = 1.2f;

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

        EventBus eventBus = new EventBus();
        eventBus.register(new MailQueue());

        Server smtpServer = new SmtpServer(smtpPort, eventBus);
        smtpServer.start();

//		fillQueueWithTestMails(25);

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

	/**
	 * Can be called to fill the queue with some test mails to make life easier
	 * @param amount int
	 */
	protected static void fillQueueWithTestMails(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "localhost");
			properties.put("mail.smtp.port", smtpPort);
			properties.put("mail.debug", "true");

			Session session = Session.getInstance(properties);

			try
			{
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress("from@example.com"));
				InternetAddress[] address = { new InternetAddress("to@example.com") };
				message.setRecipients(MimeMessage.RecipientType.TO, address);
				message.setSubject("This is just an automatic test mail (" + i + ")");
				message.setSentDate(new Date());
				message.setText("With some test content");

				Transport.send(message);
			}
			catch (MessagingException e) {}
		}

	}
}