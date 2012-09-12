package com.mockmock.mail;

public class MockMail
{
    private String from;
    private String to;
    private String subject;
    private String body;
    private String bodyHtml;
    private String rawMail;
    
    
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
}