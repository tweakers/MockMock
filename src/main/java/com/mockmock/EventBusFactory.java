package com.mockmock;

import com.google.common.eventbus.EventBus;
import com.mockmock.irc.IrcHandler;
import com.mockmock.irc.IrcServer;
import com.mockmock.mail.MailQueue;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusFactory
{
    private MailQueue mailQueue;
    private IrcHandler ircHandler;

    @Bean(name = "eventBus", autowire = Autowire.BY_NAME)
    public EventBus createEventBus()
    {
        EventBus eventBus = new EventBus();

        eventBus.register(this.mailQueue);
        eventBus.register(this.ircHandler);

        return eventBus;
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }

    @Autowired
    public void setIrcHandler(IrcHandler ircHandler) {
        this.ircHandler = ircHandler;
    }
}
