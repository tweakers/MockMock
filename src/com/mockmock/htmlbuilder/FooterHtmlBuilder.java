package com.mockmock.htmlbuilder;

public class FooterHtmlBuilder implements HtmlBuilder
{
    public String build()
    {
        String output =
                "  </body>\n" +
                "</html>\n";

        return output;
    }
}