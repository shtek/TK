package com.roman.tk;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class RomanStringUtils {
public String extractJspResponse(String string)
{
    return StringUtils.substringBetween(string,"jsp-response=\"","\">");
    // jsp-response=" ">
}
public String extractItems(String string) {
    return StringUtils.substringBetween(string, "results&quot;: [", "],");
}
public int totalNumberOfResult(String str){
    String numberOfResults = StringUtils.substringBetween(str, "totalNumberOfResults&quot;:", ",") ;
    String numberOfresultsWihtoutSpaces = numberOfResults.strip();
    int iNumberOfResults =  Integer.parseInt(numberOfresultsWihtoutSpaces);

    return iNumberOfResults;

}

}
