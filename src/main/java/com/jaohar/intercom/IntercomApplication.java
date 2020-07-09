package com.jaohar.intercom;

import com.jaohar.intercom.service.CustomerService;
import com.jaohar.intercom.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntercomApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntercomApplication.class, args);
    }


}
