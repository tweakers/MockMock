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
        StringBuilder output = new StringBuilder();

        if(mockMail != null)
        {
            MimeMessage mimeMessage = mockMail.getMimeMessage();
            try
            {
                output.append("<pre>\n");
                Enumeration<?> headers = mimeMessage.getAllHeaderLines();
                while (headers.hasMoreElements())
                {
                    String header = (String) headers.nextElement();
                    output.append(header).append("<br />");
                }
                output.append("</pre>");
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
        }

        return output.toString();
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}
