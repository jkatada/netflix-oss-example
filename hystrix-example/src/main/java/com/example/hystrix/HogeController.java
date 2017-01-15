package com.example.hystrix;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HogeController {

	@GetMapping("/hoge")
	@HystrixCommand(fallbackMethod="defaultHoge")
	public String hoge() {
		log.info("HogeController.hoge()");
		throw new IllegalStateException("Hystrix test");
	}
	
	public String defaultHoge() {
		return "serivice is down";
	}

}
