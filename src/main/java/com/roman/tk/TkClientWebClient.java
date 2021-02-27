package com.roman.tk;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.WebClient;
@Service
public class TkClientWebClient {
    private static final Logger log = LoggerFactory.getLogger(TkClientWebClient.class);
    @Value( "${goldLableUrl}" )
    private String goldLableUrl;
    public   String fetchRawData() {
       WebClient client = new WebClient();
       client.getOptions().setCssEnabled(false);
       client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        try {
             page = client.getPage(goldLableUrl);
        }catch(Exception e){
            log.error(e.toString());
            e.printStackTrace();
        }

        if (page != null) {
            return  page.asXml();
        }
        else
        {
            log.error("Page is null");
            return null;
        }
    }
}
