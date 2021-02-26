package com.roman.tk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

@Service
public class ConvertJsonToObject {
    @Autowired
    ProductItem productItem = new ProductItem();
   public List<ProductItem> jsonToObject(String json){

       List<ProductItem> items = null;

       ObjectMapper objectMapper = new ObjectMapper();

       try {
           items = objectMapper.readValue(json, new TypeReference<List<ProductItem>>(){});
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return items;
   }
}
