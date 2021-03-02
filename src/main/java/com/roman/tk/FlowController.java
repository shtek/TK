package com.roman.tk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class FlowController {
    @Autowired
    WorkerBean workerBean;
    private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    @RequestMapping("/checkFlow")
    public ResponseEntity<String> liveness() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        Runtime runtime = Runtime.getRuntime();

        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();

      System.out.println("Used memory is megabytes: "
               + bytesToMegabytes(memory)+
               "free memoery " + bytesToMegabytes(runtime.freeMemory()));


        return new ResponseEntity<>("current time -->"
              + localTime + localTime + "</br>" + workerBean.monitor()
                + "The site is up and time is " + bytesToMegabytes(runtime.totalMemory()) +
                "total memory" + "Used memory is megabytes: "
                + bytesToMegabytes(memory) + "free memoery " + bytesToMegabytes(runtime.freeMemory()),
                HttpStatus.OK);

    }
}
