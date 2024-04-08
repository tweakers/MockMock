package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

@Service
public class MailListHtmlBuilder implements HtmlBuilder
{
    private ArrayList<MockMail> mailQueue;

    public void setMailQueue(ArrayList<MockMail> mailQueue)
    {
        this.mailQueue = mailQueue;
    }

    public String build()
    {
        StringBuilder output =
                new StringBuilder("<div class=\"container\">\n");

        if(mailQueue == null || mailQueue.isEmpty())
        {
            output.append("  <h2>No emails in queue</h2>\n");
        }
        else
        {
            String mailText = mailQueue.size() == 1 ? "email" : "emails";
            output.append("  <h1>You have ").append(mailQueue.size()).append(" ").append(mailText).append("! <small class=\"deleteLink\"><a class=\"delete\" href=\"/mail/delete/all\">Delete all</a></small></h1>\n");
            output.append("  <table class=\"table table-striped\">\n");
            output.append("    <thead>\n");
            output.append("      <th>From</th>\n");
            output.append("      <th>To</th>\n");
            output.append("      <th>Subject</th>\n");
            output.append("      <th>Action</th>\n");
            output.append("    </thead>\n");
            output.append("    <tbody>\n");
            for (MockMail mail : mailQueue)
            {
                output.append(buildMailRow(mail));
            }
            output.append("    </tbody>\n");
            output.append("  </table>\n");
        }

        output.append("</div>\n");

        return output.toString();
    }

    private String buildMailRow(MockMail mail)
    {
        StringFromHtmlBuilder fromBuilder = new StringFromHtmlBuilder();
        fromBuilder.setMockMail(mail);
        String fromOutput = fromBuilder.build();

        StringRecipientHtmlBuilder recipientBuilder = new StringRecipientHtmlBuilder();
        recipientBuilder.setMaxLength(27);
        recipientBuilder.setMockMail(mail);
        recipientBuilder.setRecipientType(MimeMessage.RecipientType.TO);
        String toOutput = recipientBuilder.build();

        String subjectOutput;
        if(mail.getSubject() == null)
        {
            subjectOutput = "<em>No subject given</em>";
        }
        else
        {
            subjectOutput = StringEscapeUtils.escapeHtml(mail.getSubject());
        }

        return
            "<tr>\n" +
            "  <td>" + fromOutput + "</td>\n" +
            "  <td>" + toOutput + "</td>\n" +
            "  <td><a title=\"" + StringEscapeUtils.escapeHtml(mail.getSubject()) + "\" href=\"/view/" + mail.getId() + "\">" + subjectOutput + "</a></td>\n" +
            "  <td><a title=\"Delete this mail\" href=\"/delete/" + mail.getId() + "\"><em>Delete</em></a></td>\n" +
            "</tr>";
    }
}
