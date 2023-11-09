package com.carlos.springbootcurse;

import com.carlos.springbootcurse.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootCurseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCurseApplication.class, args);
	}
	@Bean
	public IdWorker idWorker(){
		return  new IdWorker(1,1);
	}

	
}
