package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.example.dao.preprocess;

@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
            preprocess.init();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        SpringApplication.run(Application.class, args);
    }

}
