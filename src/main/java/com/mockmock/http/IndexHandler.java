package com.mockmock.http;

import com.mockmock.htmlbuilder.FooterHtmlBuilder;
import com.mockmock.htmlbuilder.HeaderHtmlBuilder;
import com.mockmock.htmlbuilder.MailListHtmlBuilder;
import com.mockmock.mail.MailQueue;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class IndexHandler extends BaseHandler
{
    private HeaderHtmlBuilder headerHtmlBuilder;
    private FooterHtmlBuilder footerHtmlBuilder;
    private MailListHtmlBuilder mailListHtmlBuilder;

    private MailQueue mailQueue;

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse response) throws IOException, ServletException
    {
        if(!target.equals("/"))
        {
            return;
        }

        setDefaultResponseOptions(response);

        String header = headerHtmlBuilder.build();

        mailListHtmlBuilder.setMailQueue(mailQueue.getMailQueue());
        String body = mailListHtmlBuilder.build();

        String footer = footerHtmlBuilder.build();

        response.getWriter().print(header + body + footer);

        request.setHandled(true);
    }

    @Autowired
    public void setHeaderHtmlBuilder(HeaderHtmlBuilder headerHtmlBuilder) {
        this.headerHtmlBuilder = headerHtmlBuilder;
    }

    @Autowired
    public void setFooterHtmlBuilder(FooterHtmlBuilder footerHtmlBuilder) {
        this.footerHtmlBuilder = footerHtmlBuilder;
    }

    @Autowired
    public void setMailListHtmlBuilder(MailListHtmlBuilder mailListHtmlBuilder) {
        this.mailListHtmlBuilder = mailListHtmlBuilder;
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }
}
