package com.mockmock.htmlbuilder;

import com.mockmock.AppStarter;
import com.mockmock.Settings;
import com.mockmock.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeaderHtmlBuilder implements HtmlBuilder
{
    private Settings settings;

    @Autowired
    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    public String build()
    {
        String output = "";
        Util util = new Util();

        output +=
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <title>MockMock - SMTP Mock Server version " + AppStarter.VERSION_NUMBER + "</title>\n";


        if(settings.getStaticFolderPath() != null)
        {
            output +=
                "    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/css/mockmock.css\" rel=\"stylesheet\">\n";
        }
        else
        {
            output +=
                "    <style>\n" + util.getFile("/css/mockmock.css") + util.getFile("/css/bootstrap.min.css") +
                "    </style>\n";
        }

        output +=
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
                "              <li><a href=\"https://github.com/tweakers-dev/MockMock\">MockMock on Github</a></li>\n" +
                "            </ul>\n" +
                "          </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n";

        return output;
    }
}
