package com.example.Jasper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JasperApplication {

    public static void main(String[] args) {
        SpringApplication.run(JasperApplication.class, args);
    }

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

}
