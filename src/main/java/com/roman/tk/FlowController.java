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
  //  @Autowired
  //  WorkerBean workerBean;
    @RequestMapping("/checkFlow")
    public ResponseEntity<String> liveness() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = localDateTime.toLocalTime();
        return new ResponseEntity<>("current time "
            //    + localTime + localTime + "</br>" + workerBean.checkForNewArrivals(),
                + localTime + localTime + "inside check flow" +"</br>" ,
                HttpStatus.OK);

    }
}
