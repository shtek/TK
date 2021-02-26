package com.roman.tk;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.WebClient;
@Service
public class TkClientWebClient {
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
            e.printStackTrace();
        }

        return  page.asXml();
    }
}
