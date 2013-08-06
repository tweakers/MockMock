package com.mockmock.htmlbuilder;

import org.springframework.stereotype.Service;

@Service
public class FooterHtmlBuilder implements HtmlBuilder
{
    public String build()
    {
        return
                "  <script src=\"/js/jquery-1.8.1.min.js\"></script>\n" +
                "  <script src=\"/js/bootstrap.min.js\"></script>\n" +
                "  <script src=\"/js/mockmock.js\"></script>\n" +
                "  </body>\n" +
                "</html>\n";
    }
}
