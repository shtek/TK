package com.roman.tk;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

public class ProxyTest {
@Test
    public void testConnection() {
        boolean connectionStatus=false;

        try {
            InetAddress addr=InetAddress.getByName("209.203.130.59");//here type proxy server ip


            connectionStatus=addr.isReachable(1000); // 1 second time for response
            Assertions.assertTrue(connectionStatus);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }


    }
}
