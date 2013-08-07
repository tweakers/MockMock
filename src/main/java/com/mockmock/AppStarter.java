package com.mockmock;

import com.mockmock.console.Parser;
import com.mockmock.irc.IrcServer;
import com.mockmock.server.Server;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class AppStarter
{
    public static final float VERSION_NUMBER = 1.3f;

    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/beans.xml");
        BeanFactory factory = context;

        Settings settings = (Settings) factory.getBean("settings");

        // get the parameters that are allowed
        Parser parser = (Parser) factory.getBean("parser");
        settings = parser.parseOptions(args, settings);

        Server smtpServer = (Server) factory.getBean("smtpServer");
        smtpServer.setPort(settings.getSmtpPort());
        smtpServer.start();

//		fillQueueWithTestMails(settings.getSmtpPort(), 20);

		if (settings.isConnectToIrc())
		{
			IrcServer ircServer = (IrcServer) factory.getBean("ircServer");
			ircServer.setServer(settings.getIrcServer());
			ircServer.setPort(settings.getIrcPort());
			ircServer.setNickname(settings.getNickname());
			ircServer.setChannels(settings.getChannels());
			ircServer.start();
		}

        Server httpServer = (Server) factory.getBean("httpServer");
        httpServer.setPort(settings.getHttpPort());
        httpServer.start();
    }

	/**
	 * Can be called to fill the queue with some test mails to make life easier
	 * @param amount int
	 */
	protected static void fillQueueWithTestMails(int port, int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "localhost");
			properties.put("mail.smtp.port", port);
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