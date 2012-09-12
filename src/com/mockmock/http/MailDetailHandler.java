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

public class MailDetailHandler extends AbstractHandler
{
    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse response) throws IOException, ServletException
    {
        if(!isMatch(target))
        {
            return;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        HeaderHtmlBuilder headerHtmlBuilder = new HeaderHtmlBuilder();
        String header = headerHtmlBuilder.build();

        String body = "<div><p>TODO: mail detail</p></div>";

        FooterHtmlBuilder footerHtmlBuilder = new FooterHtmlBuilder();
        String footer = footerHtmlBuilder.build();

        response.getWriter().print(header + body + footer);

        request.setHandled(true);
    }

    private boolean isMatch(String target)
    {
        String pattern = "^/view/[0-9]+";
        return target.matches(pattern);
    }
}