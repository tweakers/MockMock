package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Enumeration;

@Service
public class MailViewHeadersHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;

    public String build()
    {
        String output = "";

        if(mockMail != null)
        {
            MimeMessage mimeMessage = mockMail.getMimeMessage();
            try
            {
                output += "<pre>\n";
                Enumeration headers = mimeMessage.getAllHeaderLines();
                while (headers.hasMoreElements())
                {
                    String header = (String) headers.nextElement();
                    output += header + "<br />";
                }
                output += "</pre>";
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
        }

        return output;
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}
