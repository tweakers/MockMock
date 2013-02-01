package com.mockmock.server;

import com.mockmock.AppStarter;
import com.mockmock.mail.MockMockMessageHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.server.SMTPServer;

@Service
public class SmtpServer implements Server
{
    private int port;
    private MockMockMessageHandlerFactory handlerFactory;

    public void setPort(int port)
    {
        this.port = port;
    }

    @Autowired
    public void setHandlerFactory(MockMockMessageHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    public void start()
    {
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
