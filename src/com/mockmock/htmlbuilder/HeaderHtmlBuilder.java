package com.mockmock.htmlbuilder;

import com.mockmock.mail.AppStarter;

public class HeaderHtmlBuilder implements HtmlBuilder
{
    public String build()
    {
        String output =
                        "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "  <head>\n" +
                        "    <title>MockMock - SMTP Mock Server version " + AppStarter.VERSION_NUMBER + "</title>\n" +
                        "    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                        "    <link href=\"/css/mockmock.css\" rel=\"stylesheet\">\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "  <div class=\"navbar navbar-inverse navbar-fixed-top\">\n" +
                        "    <div class=\"navbar-inner\">\n" +
                        "      <div class=\"container\">\n" +
                        "        <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n" +
                        "            <span class=\"icon-bar\"></span>\n" +
                        "            <span class=\"icon-bar\"></span>\n" +
                        "          </a>\n" +
                        "          <a class=\"brand\" href=\"/\">MockMock</a>\n" +
                        "          <div class=\"nav-collapse collapse\">\n" +
                        "            <ul class=\"nav\">\n" +
                        "              <li class=\"active\"><a href=\"/\">Home</a></li>\n" +
                        "              <li><a href=\"https://github.com/koku/MockMock\">MockMock on Github</a></li>\n" +
                        "            </ul>\n" +
                        "          </div>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </div>\n";

        return output;
    }
}