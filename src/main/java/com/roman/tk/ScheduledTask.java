package com.roman.tk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component

public class ScheduledTask {
    @Autowired
    WorkerBean workerBean;
       //every 5 seconds
      @Scheduled(fixedRate = 5000)
        public void doWork() {
           workerBean.checkForNewArrivals();
        }
    }

