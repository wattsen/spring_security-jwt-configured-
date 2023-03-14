package io.linear.controller;

import io.linear.domain.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

	@GetMapping("")
	public ResponseEntity<List<Person>> getTests() {
		List<Person> people = new ArrayList<>();
		Person person = new Person("Bai", 20);
		Person person2 = new Person("Mai", 21);
		Person person3 = new Person("Sai", 22);

		people.add(person);
		people.add(person2);
		people.add(person3);

		return new ResponseEntity<List<Person>>(
				people,
				HttpStatus.OK
		);
	}

	@GetMapping("/private")
	public ResponseEntity<String> getPrivateTest() {
		return new ResponseEntity<String>(
				"Private Page",
				HttpStatus.OK
		);
	}
}
