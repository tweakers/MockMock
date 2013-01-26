package com.mockmock.http;

import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseHandler extends AbstractHandler
{
    protected void setDefaultResponseOptions(HttpServletResponse response)
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
