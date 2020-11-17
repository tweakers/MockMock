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

        String output = "<div class=\"container\">\n";

        output +=
                "<h2>" + subjectOutput + "</h2>\n" +
                "  <div class=\"row\">\n";

        output +=
                "    <div class=\"span10\" name=\"addresses\">\n" +
                "       <h3>Addresses</h3>\n" +
                "       " + addressesHtmlBuilder.build() +
                "    </div>\n";

        output +=
                "    <div class=\"span10\" name=\"headers\">\n" +
                "       <h3>Mail headers</h3>\n" +
                "       " + headersBuilder.build() +
                "    </div>\n";

        if(mockMail.getBody() != null)
        {
            output +=
                    "    <div class=\"span10\" name=\"bodyPlainText\">\n" +
                    "       <h3>Plain text body</h3>\n" +
                    "       <div class=\"well\">" + StringEscapeUtils.escapeHtml(mockMail.getBody()) + "</div>\n" +
                    "    </div>\n";
        }

        if(mockMail.getAttacheFileName() != null)
        {
            output +=
                    "    <div class=\"span10\" name=\"bodyPlainText\">\n" +
                            "       <h3>Attachment</h3>\n" +
                            "       <div class=\"well\"><a href=\"/attachment/" +mockMail.getId() + "\">" + StringEscapeUtils.escapeHtml(mockMail.getAttacheFileName()) + "</a></div>\n" +
                            "    </div>\n";
        }

        if(mockMail.getBodyHtml() != null)
        {
//            output +=
//                    "    <div class=\"span10\" name=\"bodyHTML_Unformatted\">\n" +
//                    "       <h3>HTML body unformatted</h3>\n" +
//                    "       <div class=\"well\">" + StringEscapeUtils.escapeHtml(mockMail.getBodyHtml()) + "</div>\n" +
//                    "    </div>\n";

            // also show a parsed version via an iframe
            output +=
                    "<script type=\"text/javascript\">\n" +
                    " function SetCwinHeight(){\n" +
                    "  var iframeid=document.getElementById(\"htmliframe\"); //iframe id\n" +
                    "  if (document.getElementById){\n" +
                    "   if (iframeid && !window.opera){\n" +
                    "    if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight){\n" +
                    "     iframeid.height = iframeid.contentDocument.body.offsetHeight + 40;\n" +
                    "    }else if(iframeid.Document && iframeid.Document.body.scrollHeight){\n" +
                    "     iframeid.height = iframeid.Document.body.scrollHeight + 40;\n" +
                    "    }\n" +
                    "   }\n" +
                    "  }\n" +
                    " }\n" +
                    "</script>";

            output +=
                    "    <div class=\"span10\" name=\"iFrame\">\n" +
                    "        <h3>HTML body formatted</h3>\n" +
                    "        <div>\n" +
                    "        <iframe class=\"well\" width=\"100%\" id=\"htmliframe\"  onload=\"Javascript:SetCwinHeight()\" height=\"1\" frameborder=\"0\" src=\"/view/html/" + mockMail.getId() + "\"name=\"bodyHTML_iFrame\">\n" +
                    "        </iframe>\n" +
                    "        <div>\n" +
                    "    </div>";
        }

		// just output the raw mail so we're sure everything is on the screen
		if(mockMail.getRawMail() != null)
		{
			// output complete raw mail
			output +=
					"    <div class=\"span10\" name=\"rawOutput\">\n" +
							"       <h3>Complete raw mail output</h3>\n" +
							"       <div class=\"well\">" + StringEscapeUtils.escapeHtml(mockMail.getRawMail()) + "</div>\n" +
							"    </div>\n";
		}

        output +=
                "  </div>\n";

        output +=
                "</div>\n";

        return output;
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