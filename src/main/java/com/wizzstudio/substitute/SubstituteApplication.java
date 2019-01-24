package com.wizzstudio.substitute;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.wizzstudio.substitute.config.AdminConfigurableConfig;
import com.wizzstudio.substitute.dao.ConfigDao;
import com.wizzstudio.substitute.service.impl.ScheduledServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableSwagger2Doc
@Slf4j
public class SubstituteApplication implements CommandLineRunner {

    @Autowired
    ScheduledServiceImpl scheduledService;
    @Autowired
    ConfigDao configDao;

    public static void main(String[] args) {
        SpringApplication.run(SubstituteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("scheduled service: start");
        scheduledService.startTask();
        AdminConfigurableConfig.setConfig(configDao.findConfigById(1));

    }
}
