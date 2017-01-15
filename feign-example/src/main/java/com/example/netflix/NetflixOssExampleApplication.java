package com.example.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NetflixOssExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixOssExampleApplication.class, args);
	}
}
