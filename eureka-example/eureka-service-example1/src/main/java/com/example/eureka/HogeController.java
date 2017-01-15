package com.example.eureka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HogeController {

	@GetMapping("/hoge")
	public String hoge() {
		
		log.info("hoge1");
		return "hoge1";
	}
	
}
