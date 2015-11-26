package com.ekocaman.microservices.account.server;

import com.ekocaman.microservices.account.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.logging.Logger;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(DbConfig.class)
public class AccountServer {

    @Autowired
    protected AccountRepository accountRepository;

    protected Logger logger = Logger.getLogger(AccountServer.class.getName());

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "account-server");

        SpringApplication.run(AccountServer.class, args);
    }
}
