package com.mockmock.htmlbuilder;

import com.mockmock.Settings;
import com.mockmock.Util;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class FooterHtmlBuilder implements HtmlBuilder
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

    	if(settings.getStaticFolderPath() != null)
    	{
	    	output.append("  <script src=\"/js/jquery-1.8.1.min.js\"></script>\n" + "  <script src=\"/js/bootstrap.min.js\"></script>\n" + "  <script src=\"/js/mockmock.js\"></script>\n");
    	}
    	else
    	{
    		output.append("  <script>\n").append(util.getFile("/js/jquery-1.8.1.min.js")).append("</script>\n").append("  <script>\n").append(util.getFile("/js/bootstrap.min.js")).append("</script>\n").append("  <script>\n").append(util.getFile("/js/mockmock.js")).append("</script>\n");
    	}

    	output.append("  </body>\n" + "</html>\n");

    	return output.toString();
    }
}
