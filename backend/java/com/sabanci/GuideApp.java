package com.sabanci;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.sabanci.dao")
public class GuideApp{

	public static void main(String[] args) {
		SpringApplication.run(GuideApp.class, args);
	}


}	


