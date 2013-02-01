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
        String output = "";

        StringFromHtmlBuilder fromHtmlBuilder = new StringFromHtmlBuilder();
        fromHtmlBuilder.setMockMail(mockMail);

        StringRecipientHtmlBuilder recipientHtmlBuilder = new StringRecipientHtmlBuilder();
        recipientHtmlBuilder.setMockMail(mockMail);

        output += "From: " + fromHtmlBuilder.build() + "<br />\n";

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.TO);
        output += "To: " + recipientHtmlBuilder.build() + "<br />\n";

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.CC);
        String ccOutput = recipientHtmlBuilder.build();
        if(ccOutput.length() > 0)
        {
            output += "CC: " + ccOutput + "<br />\n";
        }

        recipientHtmlBuilder.setRecipientType(MimeMessage.RecipientType.BCC);
        String bccOutput = recipientHtmlBuilder.build();
        if(bccOutput.length() > 0)
        {
            output += "BCC: " + bccOutput + "<br />\n";
        }

        return output;
    }

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }
}
