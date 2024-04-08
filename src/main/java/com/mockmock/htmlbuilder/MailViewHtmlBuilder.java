package com.mockmock.htmlbuilder;

import com.mockmock.mail.MockMail;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailViewHtmlBuilder implements HtmlBuilder
{
    private MailViewHeadersHtmlBuilder headersBuilder;
    private AddressesHtmlBuilder addressesHtmlBuilder;

    private MockMail mockMail;

    public void setMockMail(MockMail mockMail)
    {
        this.mockMail = mockMail;
    }

    public String build()
    {
        headersBuilder.setMockMail(mockMail);

        addressesHtmlBuilder.setMockMail(mockMail);

        String subjectOutput;
        if(mockMail.getSubject() == null)
        {
            subjectOutput = "<em>No subject given</em>";
        }
        else
        {
            subjectOutput = StringEscapeUtils.escapeHtml(mockMail.getSubject());
        }

		subjectOutput += " <small class=\"deleteLink\"><a href=\"/delete/" + mockMail.getId() + "\">Delete</a></small>";

        StringBuilder output = new StringBuilder("<div class=\"container\">\n");

        output.append("<h2>")
                .append(subjectOutput)
                .append("</h2>\n")
                .append("  <div class=\"row\">\n")
                .append("    <div class=\"span10\" name=\"addresses\">\n")
                .append("       <h3>Addresses</h3>\n")
                .append("       ")
                .append(addressesHtmlBuilder.build())
                .append("    </div>\n")
                .append("    <div class=\"span10\" name=\"headers\">\n")
                .append("       <h3>Mail headers</h3>\n")
                .append("       ")
                .append(headersBuilder.build())
                .append("    </div>\n");

        if(mockMail.getBody() != null)
        {
            output.append("    <div class=\"span10\" name=\"bodyPlainText\">\n")
                    .append("       <h3>Plain text body</h3>\n")
                    .append("       <div class=\"well\">")
                    .append(StringEscapeUtils.escapeHtml(mockMail.getBody()))
                    .append("</div>\n")
                    .append("    </div>\n");
        }

        if(mockMail.getBodyHtml() != null)
        {
            output.append("    <div class=\"span10\" name=\"bodyHTML_Unformatted\">\n")
                    .append("       <h3>HTML body unformatted</h3>\n")
                    .append("       <div class=\"well\">")
                    .append(StringEscapeUtils.escapeHtml(mockMail.getBodyHtml()))
                    .append("</div>\n")
                    .append("    </div>\n");

            // also show a parsed version via an iframe
            output.append("    <div class=\"span10\" name=\"iFrame\">\n" + "        <h3>HTML body formatted</h3>\n")
                    .append("        <iframe class=\"well\" src=\"/view/html/")
                    .append(mockMail.getId())
                    .append("\" style=\"width: 780px; height: 700px; overflow: scroll;\" style=\"\" name=\"bodyHTML_iFrame\">\n")
                    .append("        </iframe>\n")
                    .append("    </div>");
        }

		// just output the raw mail so we're sure everything is on the screen
		if(mockMail.getRawMail() != null)
		{
			// output complete raw mail
			output.append("    <div class=\"span10\" name=\"rawOutput\">\n")
                    .append("       <h3>Complete raw mail output</h3>\n")
                    .append("       <div class=\"well\">")
                    .append(StringEscapeUtils.escapeHtml(mockMail.getRawMail()))
                    .append("</div>\n")
                    .append("    </div>\n");
		}

        output.append("  </div>\n")
                .append("</div>\n");

        return output.toString();
    }

    @Autowired
    public void setMailViewHeadersHtmlBuilder(MailViewHeadersHtmlBuilder mailViewHeadersHtmlBuilder) {
        this.headersBuilder = mailViewHeadersHtmlBuilder;
    }

    @Autowired
    public void setAddressesHtmlBuilder(AddressesHtmlBuilder addressesHtmlBuilder) {
        this.addressesHtmlBuilder = addressesHtmlBuilder;
    }
}
