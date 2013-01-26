package com.mockmock;

import com.google.common.eventbus.EventBus;
import com.mockmock.mail.MockMockMessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

public class SmtpServer implements Server
{
    private int port;
    private EventBus eventBus;

    public SmtpServer(int port, EventBus eventBus)
    {
        this.port = port;
        this.eventBus = eventBus;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void start()
    {
        // this handles every message that is received
        MockMockMessageHandlerFactory handlerFactory = new MockMockMessageHandlerFactory(this.eventBus);

        // start the smtp server!
        SMTPServer server = new SMTPServer(handlerFactory);
        server.setSoftwareName("MockMock SMTP Server version " + AppStarter.VERSION_NUMBER);
        server.setPort(port);

        try
        {
            System.out.println("Starting MockMock on port " + port);
            server.start();
        }
        catch (Exception e)
        {
            System.err.println("Could not start MockMock. Maybe port " + port + " is already in use?");
        }
    }
}
