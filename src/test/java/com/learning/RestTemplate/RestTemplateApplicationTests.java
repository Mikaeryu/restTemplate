package com.learning.RestTemplate;

import app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class RestTemplateApplicationTests {

	private RestTemplate restTemplate;
	private String getDogUrl = "https://dog.ceo/api/breeds/image/random";
	private String url = "http://94.198.50.185:7081/api/users";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJidWluYW0iLCJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0" +
			"VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg4OC9hcGkvdjEvc2VjdXJpdHkvbG9naW4iLCJleHAiOjE2NTc5Mjc3ODN9.i2hFeLW" +
			"EBY3YwsB1llREmZXRN53YPtIGFqPUQ4oLw6Q";

	@BeforeEach
	void beforeTest() {
		restTemplate = new RestTemplate();
	}

	@Test
	void getUsers0() {
		final ResponseEntity<ArrayList> response = restTemplate.getForEntity(url, ArrayList.class);
		HttpHeaders headers = response.getHeaders();
		String cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

		System.out.println(cookie);
		Pattern pat = Pattern.compile("\\bJSESSIONID=([^;]+)");
		Matcher matcher = pat.matcher(cookie);
		String jsessionid = "";
		if (matcher.find()) {
			jsessionid = matcher.group(0);
		}

		System.out.println(jsessionid);

//		System.out.println(response.getHeaders());
//		System.out.println(response.ok().body("data"));
		var objectList = response.getBody();
		objectList.forEach(System.out::println);
//		System.out.println(response.getStatusCode());
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void getUsers() {
		final ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
		HttpHeaders headers = response.getHeaders();

		String cookie = headers.getFirst(HttpHeaders.SET_COOKIE);
		System.out.println(cookie);
		Pattern pat = Pattern.compile("\\bJSESSIONID=([^;]+)");
		Matcher matcher = pat.matcher(cookie);
		String jsessionid = "";
		if (matcher.find()) {
			jsessionid = matcher.group(0);
		}
		System.out.println(jsessionid);

		var objectList = Arrays.asList(response.getBody());
		objectList.forEach(System.out::println);


		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void getUsers2() {
		HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
		String cookie = httpHeaders.getFirst(HttpHeaders.SET_COOKIE);

		System.out.println(cookie);
		Pattern pat = Pattern.compile("\\bJSESSIONID=([^;]+)");
		Matcher matcher = pat.matcher(cookie);
		String jsessionid = "";
		if (matcher.find()) {
			jsessionid = matcher.group(0);
		}

		System.out.println(jsessionid);
	}

	@Test
	void postUser() {
		var userToSave = new User(3, "James", "Brown", 25);
		String json = userToSave.getJson();
		HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
		HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);

		String response = restTemplate.postForObject(url, request, String.class);
		System.out.println(response);
	}

	@Test
	void contextLoads() {

	}

	@Test
	void getDogRequest() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + token);

			HttpEntity<String> entity = new HttpEntity<>("", headers);
			Object res = restTemplate.exchange(getDogUrl, HttpMethod.GET, entity, Object.class);
			System.out.println(res);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
