package com.mockmock.server;

/**
 * A simple interface for describing MockMock servers, such as the SMTP and HTTP server.
 */
public interface Server
{
    void setPort(int port);
    void start();
}
