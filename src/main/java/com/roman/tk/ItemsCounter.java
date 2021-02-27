package com.roman.tk;

import org.springframework.stereotype.Service;

@Service
public class ItemsCounter {
    private int counter = 0;
    public int getCounter() {
        return counter;
    }
    public void increment(){
         counter++;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
}
