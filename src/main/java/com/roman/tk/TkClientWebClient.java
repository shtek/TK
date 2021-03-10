package com.roman.tk;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class TkClientWebClient {
    private static final Logger log = LoggerFactory.getLogger(TkClientWebClient.class);
    @Value( "${goldLableUrl}" )
    private String goldLableUrl;
    @Value( "${herokuUrl}" )
    private String herokuUrl;

    @Deprecated
    //using web client is not possible on the cloud, as it is
    // apparently too memory intensive . or perhaps
    // the some redirections occure
    public   String oldfetchRawData() {
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
    public   String fetchRawData() {
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

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            response =  content.toString();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;

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

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            response =  content.toString();
            
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }
    public   String checkAlive() {
        URL url = null;
        String response = null;
        try {
            url = new URL(herokuUrl);
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


            response =  Integer.toString(con.getResponseCode());

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
