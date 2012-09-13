package com.mockmock.http;

import com.mockmock.htmlbuilder.FooterHtmlBuilder;
import com.mockmock.htmlbuilder.HeaderHtmlBuilder;
import com.mockmock.htmlbuilder.MailListHtmlBuilder;
import com.mockmock.mail.MailQueue;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexHandler extends BaseHandler
{
    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse response) throws IOException, ServletException
    {
        if(!target.equals("/"))
        {
            return;
        }

        setDefaultResponseOptions(response);

        HeaderHtmlBuilder headerHtmlBuilder = new HeaderHtmlBuilder();
        String header = headerHtmlBuilder.build();

        MailListHtmlBuilder mailListHtmlBuilder = new MailListHtmlBuilder();
        mailListHtmlBuilder.setMailQueue(MailQueue.getMailQueue());
        String body = mailListHtmlBuilder.build();

        FooterHtmlBuilder footerHtmlBuilder = new FooterHtmlBuilder();
        String footer = footerHtmlBuilder.build();

        response.getWriter().print(header + body + footer);

        request.setHandled(true);
    }
}