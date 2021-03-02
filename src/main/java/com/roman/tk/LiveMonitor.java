package com.roman.tk;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class LiveMonitor {
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
    @RequestMapping("/alive")
    public String liveness2() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        Runtime runtime = Runtime.getRuntime();

        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: "
                + bytesToMegabytes(memory));
        return "The site is up and time is " + bytesToMegabytes(runtime.totalMemory()) +
                "total memory" + "Used memory is megabytes: "
                + bytesToMegabytes(memory);


    }
}

