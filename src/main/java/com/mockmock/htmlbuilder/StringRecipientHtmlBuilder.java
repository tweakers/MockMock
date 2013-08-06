package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang.StringEscapeUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class StringRecipientHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;
    private int maxLength = 0;
    Message.RecipientType recipientType;

    public String build()
    {
        String output = "";
        MimeMessage mimeMessage = mockMail.getMimeMessage();

		if(mimeMessage == null)
		{
			return output;
		}

        try
        {
            Address[] addresses;
            if(recipientType == null)
            {
                addresses = mimeMessage.getAllRecipients();
            }
            else
            {
                addresses = mimeMessage.getRecipients(recipientType);
            }

            if(addresses == null)
            {
                if(recipientType == MimeMessage.RecipientType.TO)
                {
                    return mockMail.getTo();
                }
                return "";
            }

            int i = 1;
            for(Address address : addresses)
            {
                output += StringEscapeUtils.escapeHtml(address.toString());
                if(addresses.length != i)
                {
                    output += ", ";
                }

                i++;
            }
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        String shortName;
        if(maxLength > 0 && output.length() > maxLength)
        {
            shortName = output.substring(0, maxLength - 3) + "...";
        }
        else
        {
            shortName = output;
        }

        return "<span title=\"" + output + "\">" + shortName + "</title>";
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }

    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    public void setRecipientType(Message.RecipientType recipientType)
    {
        this.recipientType = recipientType;
    }
}
