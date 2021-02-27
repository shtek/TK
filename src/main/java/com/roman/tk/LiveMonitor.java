package com.roman.tk;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class LiveMonitor {
    @RequestMapping("/")
    public String liveness() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        return "The site is up and time is " + localTime.toString();
    }
}
