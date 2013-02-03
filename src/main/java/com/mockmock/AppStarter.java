package com.mockmock;

import com.mockmock.console.Parser;
import com.mockmock.irc.IrcServer;
import com.mockmock.server.Server;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppStarter
{
    public static final float VERSION_NUMBER = 1.2f;

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

        IrcServer ircServer = (IrcServer) factory.getBean("ircServer");
        ircServer.setServer(settings.getIrcServer());
        ircServer.setPort(settings.getIrcPort());
        ircServer.setNickname(settings.getNickname());
        ircServer.setChannels(settings.getChannels());
        ircServer.start();

        Server httpServer = (Server) factory.getBean("httpServer");
        httpServer.setPort(settings.getHttpPort());
        httpServer.start();
    }
}