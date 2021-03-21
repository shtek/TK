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


    public   String fetchRawData(int i) {
        URL url = null;
        String response = null;
        try {
            String urlString = goldLableUrl + i;
            url = new URL(urlString);
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


      public   String fetchRawDataViaWebClient(int i) {

        WebClient client = new WebClient();
          System.out.println("----+++-----");
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        String response;
        try {
            System.out.println("---------");
            page = client.getPage(goldLableUrl +i );
            System.out.println(page);
            response = page.asXml();
        }catch(Exception e){
            log.error(e.toString());
            e.printStackTrace();
            response=e.toString();
        }


        return response;
    }

}
