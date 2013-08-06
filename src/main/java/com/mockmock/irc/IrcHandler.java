package com.mockmock.irc;

import com.google.common.eventbus.Subscribe;
import com.mockmock.mail.MailQueue;
import com.mockmock.mail.MockMail;
import com.sorcix.sirc.Channel;
import com.sorcix.sirc.IrcAdaptor;
import com.sorcix.sirc.IrcConnection;
import com.sorcix.sirc.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class IrcHandler extends IrcAdaptor
{
    private IrcConnection connection;
    private MailQueue mailQueue;

    private Set<String> channels = new HashSet<>();

    @Override
    public void onMessage(IrcConnection irc, User sender, Channel target, String message)
    {
        System.out.println(sender.getNick()+": "+message);

        if (message.equals("!lastmail"))
        {
            MockMail lastMail = this.mailQueue.getLastSendMail();
            if (lastMail != null)
                this.postNewMailToChannel(lastMail, target);
        }
    }

    @Subscribe
    public void postNewMailToChannels(MockMail mail)
    {
        for (String channel : this.channels)
        {
            Channel chan = this.connection.createChannel(channel);
            this.postNewMailToChannel(mail, chan);
        }
    }

    public void postNewMailToChannel(MockMail mail, Channel channel)
    {
        channel.sendMessage("Send new mail: " + mail.getTo() + ", " + mail.getSubject());
    }

    @Override
    public void onConnect(IrcConnection irc)
    {
        for (String channel : this.channels)
            irc.createChannel(channel).join();
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    public void setConnection(IrcConnection connection) {
        this.connection = connection;
    }
}
