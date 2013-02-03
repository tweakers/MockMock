package com.mockmock.server;

/**
 * A simple interface for describing MockMock servers, such as the SMTP and HTTP server.
 */
public interface Server
{
    public void setPort(int port);
    public void start();
}
