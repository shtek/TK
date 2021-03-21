package com.roman.tk;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class RomanStringUtils {

private String romanStrip(String string){
    return   string.replaceAll("^[ \t]+|[ \t]+$", "") ;
}




    public String extractJspResponse(String string)
    {
        return StringUtils.substringBetween(string,"jsp-response=\"","\">");
        // jsp-response=" "> or single quote if using the simple URL java
    }
//public String extractItems(String string) {
//    return StringUtils.substringBetween(string, "results&#034;: [", "],");
//}

    public String extractItems(String string) {
        return StringUtils.substringBetween(string, "results&quot;: [", "],");
    }
public int totalNumberOfResult(String str){

    String numberOfResults = StringUtils.substringBetween(str, "totalNumberOfResults&quot;:", ",") ;
    String numberOfresultsWihtoutSpaces = romanStrip(numberOfResults);

    int iNumberOfResults =  Integer.parseInt(numberOfresultsWihtoutSpaces);

    return iNumberOfResults;

}
/*
;numberOfPages&#034;: 5  },
we need to extract 5
 */
public int totalNumberOfPages(String str){
    String numberOfResults = StringUtils.substringBetween(str, "numberOfPages&#034;:", "}") ;
    String numberOfresultsWihtoutSpaces = romanStrip(numberOfResults);
    int iNumberOfResults =  Integer.parseInt(numberOfresultsWihtoutSpaces);
    return iNumberOfResults;
}

}
