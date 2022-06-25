package com.podzirei.springonlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan(basePackages = {"com.podzirei.springonlineshop.entity"})
//@EnableJpaRepositories(basePackages = {"com.podzirei.springonlineshop.repository"})
@SpringBootApplication
public class SpringOnlineShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOnlineShopApplication.class, args);
	}

}
