package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;

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
        String output = "<div id=\"mailQueue\">\n";

        if(mailQueue == null || mailQueue.size() == 0)
        {
            output += "<p>Hmm, no mails yet. That makes me sad :'( ...</p>";
        }
        else
        {
            output += "  <table>\n";

            for (MockMail mail : mailQueue)
            {
                output += buildMailRow(mail);
            }

            output += "  </table>\n";
        }

        output += "</div>\n";

        return output;
    }

    private String buildMailRow(MockMail mail)
    {
        String output =
                        "<tr><td>" + mail.getSubject() + "</td></tr>\n";

        return output;
    }
}