package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang.StringEscapeUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class StringFromHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;

    public String build()
    {
        StringBuilder output = new StringBuilder();
        MimeMessage mimeMessage = mockMail.getMimeMessage();

		if(mimeMessage == null)
		{
			return output.toString();
		}

        try
        {
            Address[] addresses = mimeMessage.getFrom();
            if(addresses != null)
            {
                int i = 1;
                for(Address address : addresses)
                {
                    output.append(StringEscapeUtils.escapeHtml(address.toString()));
                    if(addresses.length != i)
                    {
                        output.append(", ");
                    }

                    i++;
                }
            }
            else
            {
                output.append(StringEscapeUtils.escapeHtml(mockMail.getFrom()));
            }
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        return output.toString();
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}
