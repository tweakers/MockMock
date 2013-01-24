package com.mockmock;

/**
 * An simple interface for describing MockMock servers, such as the SMTP and HTTP server.
 */
public interface Server
{
    public void setPort(int port);
    public void start();
}
