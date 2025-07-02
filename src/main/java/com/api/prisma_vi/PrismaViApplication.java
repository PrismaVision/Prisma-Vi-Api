package com.api.prisma_vi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableFeignClients
public class PrismaViApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrismaViApplication.class, args);
	}

}
