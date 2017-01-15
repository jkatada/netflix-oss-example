package com.example.netflix.feign;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.netflix.common.User;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;

@RestController
@RequestMapping("/feign")
public class FeignController {

	private final RestTemplate restTemplate;
	private final UserClient userClient;
	@Autowired
	UserClientSpring userClientSpring;

	public FeignController(RestTemplateBuilder builder) {
		this.userClient = Feign.builder().decoder(new JacksonDecoder())
				.target(UserClient.class, "http://localhost:8080");
		this.restTemplate = builder.build();

	}

	// Feignを使った場合1
	@GetMapping("/user/{id}")
	public User user(@PathVariable long id) {
		User user = userClient.user(id);
		user.setName(user.getName() + " via Feign");
		return user;
	}

	// Feignを使った場合2
	@GetMapping("/user")
	public List<User> user() {
		return userClient.user().stream().map(u -> {
			u.setName(u.getName() + " via Feign");
			return u;
		}).collect(Collectors.toList());
	}

	// RestTemplateを使った場合
	@GetMapping("/restTemplate/{id}")
	public User restTemplateUser(@PathVariable long id) {
		User user = restTemplate
				.getForObject("http://localhost:8080/feigndummy/user/" + id, User.class);
		user.setName(user.getName() + " via RestTemplate");
		return user;
	}

	// Feign + Spring Cloud Netflix を使った場合
	@GetMapping("/feignSpring/{id}")
	public User feignSpringUser(@PathVariable long id) {
		User user = userClientSpring.user(id);
		user.setName(user.getName() + " via Feign with Spring Cloud Netflix");
		return user;
	}

	public interface UserClient {
		@RequestLine("GET /feigndummy/user/{id}")
		User user(@Param("id") long id);

		@RequestLine("GET /feigndummy/user")
		List<User> user();
	}
	
	// @FeignClientのインターフェースは@Autowiredでインジェクションできる
	// @FeignClientを付与するとHystrixも有効化
	@FeignClient(name="userClient", url="http://localhost:8080")
	public interface UserClientSpring {
		// Springの@ReqeustMappingが使える。@GetMappingだとエラー
		@RequestMapping(method = RequestMethod.GET, value = "/feigndummy/user/{id}")
		User user(@PathVariable("id") long id);

		@RequestMapping(method = RequestMethod.GET, value = "/feigndummy/user")
		List<User> user();
	}

}
