package com.mockmock.htmlbuilder;

import com.mockmock.mail.AppStarter;

public class HeaderHtmlBuilder implements HtmlBuilder
{
    public String build()
    {
        String output =
                        "<html>\n" +
                        "  <head>\n" +
                        "    <title>MockMock - SMTP Mock Server version " + AppStarter.VERSION_NUMBER + "</title>\n" +
                        "  </head>\n" +
                        "  <body>\n";

        return output;
    }
}