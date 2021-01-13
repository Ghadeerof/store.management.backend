package com.maids.cc.store.management.backend;

import com.maids.cc.store.management.backend.repository.*;
import com.maids.cc.store.management.backend.seeder.Seeder;
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
@ComponentScan({
		"com.maids.cc.store.management.backend.config",
		"com.maids.cc.store.management.backend.controller",
		"com.maids.cc.store.management.backend.service",
		"com.maids.cc.store.management.backend.repository"
})
@EntityScan("com.maids.cc.store.management.backend.entity")
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	DataSource dataSource;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	LocationRepository locationRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SaleRepository saleRepository;

	@Autowired
	SaleItemRepository saleItemRepository;

	@Autowired
	SellerRepository sellerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		try{
			System.out.println("Our DataSource is = " + dataSource);
			//seedDatabase();
		}catch (Exception ex){
			System.out.println("Store Management Application : " + ex.getMessage());
		}
	}


	private void seedDatabase(){
		new Seeder(categoryRepository,clientRepository,locationRepository,productRepository,
				saleItemRepository,saleRepository, sellerRepository)
				.seedDb();
	}
}
