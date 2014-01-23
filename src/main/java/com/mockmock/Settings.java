package com.mockmock;

import com.sorcix.sirc.IrcServer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class Settings
{
    /**
     * The default port where MockMock will run on
     */
    private int smtpPort = 25000;

    /**
     * The default port for the http server
     */
    private int httpPort = 8282;

    /**
     * The maximum size the mail queue may be
     */
    private int maxMailQueueSize = 1000;

	/**
	 * Whether or not to connect to the IRC server
	 */
	private boolean connectToIrc = false;

    /**
     * The irc server to connect to
     */
    private String ircServer = "irc.tweakers.net";

    /**
     * The port of the irc server to connect to
     */
    private int ircPort = IrcServer.DEFAULT_PORT;

    /**
     * The nickname to use on irc
     */
    private String nickname = "mockmock";

    /**
     * A set of irc channels to join
     */
    private Set<String> channels = new HashSet<>();

	/**
	 * A set of email addresses to filter
	 */
    private Set<String> filterEmailAddresses = new HashSet<>();


    public Settings()
    {
        this.channels.add("#postman");
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getMaxMailQueueSize() {
        return maxMailQueueSize;
    }

    public void setMaxMailQueueSize(int maxMailQueueSize) {
        this.maxMailQueueSize = maxMailQueueSize;
    }

    public String getIrcServer() {
        return ircServer;
    }

    public void setIrcServer(String ircServer) {
        this.ircServer = ircServer;
    }

    public int getIrcPort() {
        return ircPort;
    }

    public void setIrcPort(int ircPort) {
        this.ircPort = ircPort;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

	public boolean isConnectToIrc() {
		return connectToIrc;
	}

	public void setConnectToIrc(boolean connectToIrc) {
		this.connectToIrc = connectToIrc;
	}

	public Set<String> getFilterEmailAddresses() {
		return filterEmailAddresses;
	}

	public void setFilterEmailAddresses(Set<String> filterEmailAddresses) {
		this.filterEmailAddresses = filterEmailAddresses;
	}
}
