package com.mockmock.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class MockMail implements Comparable<MockMail>
{
    private long id;
    private String from;
    private String to;
    private String subject;
    private String body;
    private String bodyHtml;
    private String rawMail;
    private MimeMessage mimeMessage;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFrom()
    {
        return from;
    }
    
    public void setFrom(String from)
    {
        this.from = from;
    }
    
    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }
    
    public String getSubject() 
    {
        return subject;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getRawMail()
    {
        return rawMail;
    }

    public void setRawMail(String rawMail)
    {
        this.rawMail = rawMail;
    }

    public String getBodyHtml() 
    {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) 
    {
        this.bodyHtml = bodyHtml;
    }

    public MimeMessage getMimeMessage()
    {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage)
    {
        this.mimeMessage = mimeMessage;
    }

    @Override
    public int compareTo(MockMail o)
    {
        try
        {
            Date sentDate = mimeMessage.getSentDate();
            long sentTime = sentDate.getTime();

            Date sentDate2 = o.getMimeMessage().getSentDate();
            long receivedTime2 = sentDate2.getTime();

            long diff = sentTime - receivedTime2;
            return (int) diff;
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}