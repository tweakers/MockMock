package com.mockmock.irc;

import com.sorcix.sirc.IrcConnection;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IrcConnectionFactory
{
    @Bean(name = "ircConnection", autowire = Autowire.BY_NAME)
    public IrcConnection getIrcConnection()
    {
        return new IrcConnection();
    }
}
