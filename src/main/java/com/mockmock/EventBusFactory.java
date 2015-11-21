package com.mockmock;

import com.google.common.eventbus.EventBus;
import com.mockmock.mail.MailQueue;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusFactory
{
    private MailQueue mailQueue;

    @Bean(name = "eventBus", autowire = Autowire.BY_NAME)
    public EventBus createEventBus()
    {
        EventBus eventBus = new EventBus();

        eventBus.register(this.mailQueue);

        return eventBus;
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }
}
