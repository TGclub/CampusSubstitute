package com.wizzstudio.substitute;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.wizzstudio.substitute.service.impl.ScheduledServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc
public class SubstituteApplication implements CommandLineRunner {

    @Autowired
    ScheduledServiceImpl scheduledService;
    public static void main(String[] args) {
        SpringApplication.run(SubstituteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scheduledService.checkOutOfTimeIndent();
    }
}
