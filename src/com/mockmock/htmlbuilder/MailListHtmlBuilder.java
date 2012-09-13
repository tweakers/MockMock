package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;

public class MailListHtmlBuilder implements HtmlBuilder
{
    private ArrayList<MockMail> mailQueue;

    public void setMailQueue(ArrayList<MockMail> mailQueue)
    {
        this.mailQueue = mailQueue;
    }

    public String build()
    {
        String output =
                    "<div class=\"container\">\n";

        if(mailQueue == null || mailQueue.size() == 0)
        {
            output += "  <h2>Hmm, no mails yet. That makes me sad :'(</h2>\n";
        }
        else
        {
            String mailText = mailQueue.size() == 1 ? "mail" : "mails";
            output += "  <h1>I've got " + mailQueue.size() + " " + mailText + " for you. Nice!</h1>\n";
            output += "  <table class=\"table table-striped\">\n";
            output += "    <thead>\n";
            output += "      <th>From</th>\n";
            output += "      <th>To</th>\n";
            output += "      <th>Subject</th>\n";
            output += "    </thead>\n";

            output += "    <tbody>\n";
            for (MockMail mail : mailQueue)
            {
                output += buildMailRow(mail);
            }
            output += "    </tbody>\n";

            output += "  </table>\n";
        }

        output += "</div>\n";

        return output;
    }

    private String buildMailRow(MockMail mail)
    {
        String output =
                        "<tr>\n" +
                        "  <td>" + StringEscapeUtils.escapeHtml4(mail.getFrom()) + "</td>\n" +
                        "  <td>" + StringEscapeUtils.escapeHtml4(mail.getTo()) + "</td>\n" +
                        "  <td><a href=\"/view/" + mail.getId() + "\">" + StringEscapeUtils.escapeHtml4(mail.getSubject()) + "</a></td>\n" +
                        "</tr>";

        return output;
    }
}