package com.aira.airapiplatform;

import com.aira.airapiclientsdk.client.AirapiClient;
import com.aira.airapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AirapiPlatformApplicationTests {
    @Autowired
    private AirapiClient airapiClient;
    @Test
    void contextLoads() {
    }

    @Test
    void testGet(){
        User user = new User();
        user.setUserName("八嘎狗");
        String userNameByPost = airapiClient.getUserNameByPost(user);
        System.out.println(userNameByPost);
    }
}
