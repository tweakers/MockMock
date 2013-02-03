package com.mockmock.http;

import com.mockmock.htmlbuilder.FooterHtmlBuilder;
import com.mockmock.htmlbuilder.HeaderHtmlBuilder;
import com.mockmock.htmlbuilder.MailViewHtmlBuilder;
import com.mockmock.mail.MailQueue;
import com.mockmock.mail.MockMail;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MailDetailHandler extends BaseHandler
{
    private String pattern = "^/view/([0-9]+)/?$";

    private HeaderHtmlBuilder headerHtmlBuilder;
    private FooterHtmlBuilder footerHtmlBuilder;
    private MailViewHtmlBuilder mailViewHtmlBuilder;

    private MailQueue mailQueue;

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse response) throws IOException, ServletException
    {
        if(!isMatch(target))
        {
            return;
        }

        long mailId = getMailId(target);
        if(mailId == 0)
        {
            return;
        }

        MockMail mockMail = this.mailQueue.getById(mailId);
        if(mockMail == null)
        {
            return;
        }

        setDefaultResponseOptions(response);

        String header = headerHtmlBuilder.build();

        mailViewHtmlBuilder.setMockMail(mockMail);
        String body = mailViewHtmlBuilder.build();

        String footer = footerHtmlBuilder.build();

        response.getWriter().print(header + body + footer);

        request.setHandled(true);
    }

    /**
     * Checks if this handler should be used for the given target
     * @param target String
     * @return boolean
     */
    private boolean isMatch(String target)
    {
        return target.matches(pattern);
    }

    /**
     * Returns the mail id if it is part of the target
     * @param target String
     * @return long
     */
    private long getMailId(String target)
    {
        Pattern compiledPattern = Pattern.compile(pattern);

        Matcher matcher = compiledPattern.matcher(target);
        if(matcher.find())
        {
            String result = matcher.group(1);
            try
            {
                return Long.valueOf(result);
            }
            catch (NumberFormatException e)
            {
                return 0;
            }
        }

        return 0;
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
    public void setMailViewHtmlBuilder(MailViewHtmlBuilder mailViewHtmlBuilder) {
        this.mailViewHtmlBuilder = mailViewHtmlBuilder;
    }

    @Autowired
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }
}
