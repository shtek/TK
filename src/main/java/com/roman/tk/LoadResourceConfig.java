package com.roman.tk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class LoadResourceConfig {
    //@Value("classpath:/static/brands.txt")
    private Resource resource = new DefaultResourceLoader().getResource("classpath:/static/brands.txt");
    public List<String> brands;
    public LoadResourceConfig(){
        setBrands();
        //ResourceLoader resourceLoader = new DefaultResourceLoader();
        // resource = resourceLoader.getResource("classpath:/static/brands.txt");
    }
    private  String asString() {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch ( IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public void setBrands() {
        String lines[] = asString().split("\\r?\\n");
        brands = Arrays.asList(lines);

    }
    public List<String> getBrands(){
          return  brands;
    }


}
