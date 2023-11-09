package com.socialLoginTest.kakaoLoginTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KakaoLoginTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(KakaoLoginTestApplication.class, args);
    }

}
