package com.example.accessingdatar2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessingDataR2dbcApplication {

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
			repository.saveAll(Arrays.asList(new Account("Jack", "Bauer"),
					new Account("Chloe", "O'Brian"),
					new Account("Kim", "Bauer"),
					new Account("David", "Palmer"),
					new Account("Michelle", "Dessler")))
					.blockLast(Duration.ofSeconds(10));

			// fetch all customers
			log.info("Account found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(customer -> {
				log.info(customer.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L).doOnNext(customer -> {
				log.info("Account found with findById(1L):");
				log.info("--------------------------------");
				log.info(customer.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));


			// fetch customers by last name
			log.info("Account found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").doOnNext(bauer -> {
				log.info(bauer.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");
		};
	}

}
