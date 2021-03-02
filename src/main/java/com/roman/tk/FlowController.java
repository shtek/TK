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
        // Get current size of heap in bytes
        long heapSize = Runtime.getRuntime().totalMemory();

// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        long heapMaxSize = Runtime.getRuntime().maxMemory();

        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        long heapFreeSize = Runtime.getRuntime().freeMemory();;


        return new ResponseEntity<>("current time -->"
              + localTime + localTime + "</br>" + workerBean.monitor()
                + "The site is up and time is heapMaxSize" + bytesToMegabytes(heapMaxSize) +
                "heapSize: "
                + bytesToMegabytes(heapSize) + "free memoery " + bytesToMegabytes(heapFreeSize),
                HttpStatus.OK);

    }
}
