package com.mockmock.http;

import com.mockmock.mail.MailQueue;
import com.mockmock.mail.MockMail;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pengzili on 2016/12/30.
 */

@Service
public class MailAttachmentHandler extends BaseHandler{

    private String pattern = "^/attachment/([0-9]+)/?$";

    private MailQueue mailQueue;

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException, ServletException {

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

        if(mockMail.getBodyHtml() == null)
        {
            return;
        }

        String fileName = mockMail.getAttacheFileName();
        String fileNameExt =fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        String mimeType = "application/octet-stream";

        if (fileNameExt.equals("pdf")){
            mimeType = "application/pdf";
        }else if(fileNameExt.equals("doc")){
            mimeType = "application/msword";
        }else if(fileNameExt.equals("xls")){
            mimeType = "application/vnd.ms-excel";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", " attachment;filename="+ MimeUtility.encodeWord(mockMail.getAttacheFileName()));
        response.getOutputStream().write(mockMail.getAttachment());
        response.setStatus(HttpServletResponse.SC_OK);

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
    public void setMailQueue(MailQueue mailQueue) {
        this.mailQueue = mailQueue;
    }
}
