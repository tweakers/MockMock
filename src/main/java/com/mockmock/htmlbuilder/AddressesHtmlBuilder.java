package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class AddressesHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;

    public String build()
    {
        StringBuilder output = new StringBuilder();

        StringFromHtmlBuilder fromHtmlBuilder = new StringFromHtmlBuilder();
        fromHtmlBuilder.setMockMail(mockMail);

        StringRecipientHtmlBuilder recipientHtmlBuilder = new StringRecipientHtmlBuilder();
        recipientHtmlBuilder.setMockMail(mockMail);

        output.append("From: ").append(fromHtmlBuilder.build()).append("<br />\n");

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.TO);
        output.append("To: ").append(recipientHtmlBuilder.build()).append("<br />\n");

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.CC);
        String ccOutput = recipientHtmlBuilder.build();
        if(ccOutput.length() > 0)
        {
            output.append("CC: ").append(ccOutput).append("<br />\n");
        }

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.BCC);
        String bccOutput = recipientHtmlBuilder.build();
        if(bccOutput.length() > 0)
        {
            output.append("BCC: ").append(bccOutput).append("<br />\n");
        }

        return output.toString();
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}
