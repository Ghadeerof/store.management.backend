package com.maids.cc.store.management.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@SpringBootApplication
//@ComponentScan({""})
@EntityScan("com.maids.cc.store.management.backend.entity")
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		try{
			System.out.println("Our DataSource is = " + dataSource);
		}catch (Exception ex){
			System.out.println("Store Management Application : " + ex.getMessage());
		}
	}
}
