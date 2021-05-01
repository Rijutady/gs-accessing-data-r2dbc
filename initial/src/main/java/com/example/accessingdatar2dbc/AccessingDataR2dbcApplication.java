package com.example.accessingdatar2dbc;

import com.example.accessingdatar2dbc.Account;
import com.example.accessingdatar2dbc.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;


import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class AccessingDataR2dbcApplication {

	private static final Logger log = LoggerFactory.getLogger(AccessingDataR2dbcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataR2dbcApplication.class, args);
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

		return initializer;
	}

	@Bean
	public CommandLineRunner demo(AccountRepository repository) {

		return (args) -> {
			// save a few customers
			repository.saveAll(Arrays.asList(new Account("123456789", "Keri Lee"),
					new Account("123456001", "Dollie R. Schnidt"),
					new Account("123456002", "Cornelia J. LeClerc"),
					new Account("123456003", "Cynthia Rau"),
					new Account("123456004", "Douglas R. Cobbs")))
					.blockLast(Duration.ofSeconds(10));

			// fetch all customers
			log.info("Account found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L).doOnNext(account -> {
				log.info("Account found with findById(1L):");
				log.info("--------------------------------");
				log.info(account.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));


//			// fetch customers by last name
//			log.info("Account found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").doOnNext(bauer -> {
//				log.info(bauer.toString());
//			}).blockLast(Duration.ofSeconds(10));;
//			log.info("");
		};
	}

}
