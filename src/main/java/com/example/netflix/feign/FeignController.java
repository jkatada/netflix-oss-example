package com.example.netflix.feign;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netflix.common.User;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;

@RestController
@RequestMapping("/feign")
public class FeignController {

	@GetMapping("/user/{id}")
	public User user(@PathVariable long id) {

		FeignDummy feignDummy = Feign.builder().decoder(new JacksonDecoder())
				.target(FeignDummy.class, "http://localhost:8080");

		User user = feignDummy.user(id);
		user.setName(user.getName() + " via Feign");
		return user;
	}

	@GetMapping("/user")
	public List<User> user() {

		FeignDummy feignDummy = Feign.builder().decoder(new JacksonDecoder())
				.target(FeignDummy.class, "http://localhost:8080");

		return feignDummy.user().stream().map(u -> {
			u.setName(u.getName() + " via Feign");
			return u;
		}).collect(Collectors.toList());
	}

	public interface FeignDummy {
		@RequestLine("GET /feigndummy/user/{id}")
		User user(@Param("id") long id);

		@RequestLine("GET /feigndummy/user")
		List<User> user();

	}

}
