package com.mockmock.http;

import com.mockmock.mail.MailQueue;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteHandler extends BaseHandler
{
    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest,
                       HttpServletResponse response) throws IOException, ServletException
    {
        if(!target.equals("/mail/delete/all"))
        {
            return;
        }

        setDefaultResponseOptions(response);

        // empty the mail queue
        MailQueue.emptyQueue();

        setDefaultResponseOptions(response);
        request.setHandled(true);
    }
}
