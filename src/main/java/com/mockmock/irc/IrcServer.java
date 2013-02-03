package com.mockmock.irc;

import com.mockmock.server.Server;
import com.mockmock.mail.MailQueue;
import com.sorcix.sirc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class IrcServer implements Server
{
    private IrcConnection connection;
    private MailQueue mailQueue;
    private IrcHandler ircHandler;

    private String server;
    private int port = com.sorcix.sirc.IrcServer.DEFAULT_PORT;
    private String nickname;
    private Set<String> channels = new HashSet<>();

    @Override
    public void start()
    {
        try {
            this.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NickNameException e) {
            this.nickname = this.nickname + "1337";
            this.start();
        } catch (PasswordException e) {
            e.printStackTrace();
        }
    }

    protected void connect() throws PasswordException, IOException, NickNameException {
        this.ircHandler.setChannels(this.channels);

        this.connection.setServerAddress(this.server);
        this.connection.setServerPort(this.port);
        this.connection.setNick(this.nickname);
        this.connection.addServerListener(this.ircHandler);
        this.connection.addMessageListener(this.ircHandler);

        this.connection.connect();
        this.ircHandler.setConnection(this.connection);

        System.out.println("Connected to " + this.server);
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    @Override
    public void setPort(int port)
    {
        this.port = port;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }

    @Autowired
    public void setIrcHandler(IrcHandler ircHandler) {
        this.ircHandler = ircHandler;
    }

    @Autowired
    public void setConnection(IrcConnection connection) {
        this.connection = connection;
    }
}
