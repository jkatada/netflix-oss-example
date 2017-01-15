package com.example.eureka;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HogeController {

	private static final String SERVICE_ID = "eureka-service-example";
	private static final String SERVICE_URL = "http://" + SERVICE_ID + "/hoge";

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DiscoveryClient discoveryClient;

	@GetMapping("/hoge")
	public String hoge() {
		String result = restTemplate.getForEntity(SERVICE_URL, String.class)
				.getBody();
		log.info("result: " + result);

		return result;
	}

	@GetMapping("/allInstances")
	public List<String> allInstance() {
		List<String> result = discoveryClient.getInstances(SERVICE_ID).stream()
				.map(i -> i.getUri().toString()).collect(Collectors.toList());
		return result;
	}


}
