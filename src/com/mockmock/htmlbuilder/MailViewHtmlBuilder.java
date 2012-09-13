package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang3.StringEscapeUtils;

public class MailViewHtmlBuilder implements HtmlBuilder
{
    private MockMail mockMail;

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }

    public String build()
    {
        String output = "<div class=\"container\">\n";

        output +=
                "<h2>" + StringEscapeUtils.escapeHtml4(mockMail.getSubject()) + "</h2>\n" +
                "  <div class=\"row\">\n" +
                "    <div class=\"span10\">\n" +
                "       <pre>" + StringEscapeUtils.escapeHtml4(mockMail.getBody()) + "</pre>\n" +
                "    </div>\n" +
                "  </div>\n";

        output += "</div>\n";

        return output;
    }
}