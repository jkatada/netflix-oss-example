package com.example.netflix.feign;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.netflix.common.User;

@RestController
@RequestMapping("/feigndummy")
public class DummyController {

	@GetMapping("/user/{id}")
	public User dummy(@PathVariable("id") long id) {
		return new User(id, "taro", 20);
	}

	@GetMapping("/user")
	public List<User> dummy() {
		return Arrays.asList(new User(1, "taro", 20), new User(2, "jiro", 15),
				new User(3, "hanako", 10));
	}

}
