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
        StringBuilder output = new StringBuilder();
        Util util = new Util();

        output.append("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "  <head>\n" + "    <title>MockMock - SMTP Mock Server version " + AppStarter.VERSION_NUMBER + "</title>\n");

        if(settings.getStaticFolderPath() != null)
        {
            output.append("    <link href=\"/css/bootstrap.min.css\" rel=\"stylesheet\">\n")
                    .append("    <link href=\"/css/mockmock.css\" rel=\"stylesheet\">\n");
        }
        else
        {
            output.append("    <style>\n")
                    .append(util.getFile("/css/mockmock.css"))
                    .append(util.getFile("/css/bootstrap.min.css"))
                    .append("    </style>\n");
        }

        output.append("  </head>\n")
                .append("  <body>\n")
                .append("  <div class=\"navbar navbar-inverse navbar-fixed-top\">\n")
                .append("    <div class=\"navbar-inner\">\n")
                .append("      <div class=\"container\">\n")
                .append("        <a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n")
                .append("            <span class=\"icon-bar\"></span>\n")
                .append("            <span class=\"icon-bar\"></span>\n")
                .append("          </a>\n")
                .append("          <a class=\"brand\" href=\"/\">MockMock</a>\n")
                .append("          <div class=\"nav-collapse collapse\">\n")
                .append("            <ul class=\"nav\">\n")
                .append("              <li class=\"active\"><a href=\"/\">Home</a></li>\n")
                .append("              <li><a href=\"https://github.com/tweakers-dev/MockMock\">MockMock on Github</a></li>\n")
                .append("            </ul>\n")
                .append("          </div>\n")
                .append("      </div>\n")
                .append("    </div>\n")
                .append("  </div>\n");

        return output.toString();
    }
}
