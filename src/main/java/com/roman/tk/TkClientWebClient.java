package com.roman.tk;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
    public   String fetchRawDataForTestPurposes() {
        URL url = null;
        String response = null;
        try {
            url = new URL(goldLableUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            String body = con.getResponseMessage();
            response = Integer.toString(status) + body;
            
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }
    /*
      public   String fetchRawDataForTestPurposes() {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        String response;
        try {
            page = client.getPage(goldLableUrl);
            response = page.toString();
        }catch(Exception e){
            log.error(e.toString());
            e.printStackTrace();
            response=e.toString();
        }


        return response;
    }
     */
}
