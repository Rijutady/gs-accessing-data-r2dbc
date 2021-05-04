package com.example.accessingdatar2dbc;

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
			// save a few accounts
			repository.saveAll(Arrays.asList(new Account( "123456789", "Lee", "Keri", 1233L),
					new Account("123456001", "Schnidt", "Dollie R. ", 35L),
					new Account("123456002", "LeClerc", "Cornelia J. ", 927L),
					new Account("123456003", "Rau", "Cynthia", 4567L),
					new Account("123456004", "Cobbs", "Douglas R. ", 423L),
					new Account("123456005", "Patel", "Michael", 3462L),
					new Account("123456006", "Wong", "Suzanne", 100L),
					new Account("123456007", "Jaya", "Ivan C. ", 5676L),
					new Account("123456008", "Ketterer", "Ida", 4553L),
					new Account("123456009", "Lucero", "Laina Ochoa", 5789L),
					new Account("123456010", "Montana", "Wesley M. ", 8912L),
					new Account("123456011", "McCleary", "Leslie F. ", 2300L),
					new Account("123456012", "Mudra", "Sayeed D. ", 3200L),
					new Account("123456013", "Domingo", "Pietronella J. ", 730L),
					new Account("123456014", "Leary", "John S. O ", 430L),
					new Account("123456015", "Smith", "Gladys D. ", 250L),
					new Account("123456016", "Thygesen", "Sally O. ", 202L),
					new Account("123456017", "Vogt", "Rachel", 2044L),
					new Account("123456018", "DeLong", "Julia", 2543L),
					new Account("123456019", "Rizzoli", "Mark T. ", 3453L),
					new Account("123456020", "Angelo", "Maria J. ", 2676L)
					))
					.blockLast(Duration.ofSeconds(10));

			// delete account by ID
			log.info("");
			log.info("Delete by number 123456001");
			log.info("--------------------------------------------");
			repository.deleteByNumber("123456006").doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");

			// fetch all accounts
			log.info("Account found with findAll():");
			log.info("-------------------------------");
			repository.findAll().doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));

			log.info("");

			// fetch an individual account by ID
			repository.findById(1L).doOnNext(account -> {
				log.info("Account found with findById(1L):");
				log.info("--------------------------------");
				log.info(account.toString());
				log.info("");
			}).block(Duration.ofSeconds(10));


			// fetch accounts by last name
			log.info("Account found with findByLastName(\"Rau\"):");
			log.info("--------------------------------------------");
			repository.findByLastName("Rau").doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");

			// fetch top 2 accounts by balance
			log.info("Top 2 balance accounts:");
			log.info("--------------------------------------------");
			repository.searchTopTwoByBalance().doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");

			// fetch accounts by last name that contains search string
			log.info("Account found with findByOwnerContainingIgnoreCase(\"tana\")");
			log.info("--------------------------------------------");
			repository.findByOwnerContainingIgnoreCase().doOnNext(account -> {
				log.info(account.toString());
			}).blockLast(Duration.ofSeconds(10));;
			log.info("");
		};
	}

}
