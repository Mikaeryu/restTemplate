package com.learning.copy;

import app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class RestTemplateApplicationTestsCopy {

	private RestTemplate restTemplate;
	private String getUrl = "http://94.198.50.185:7081/api/users";

	@BeforeEach
	void beforeTest() {
		restTemplate = new RestTemplate();
	}

	@Test
	void getUsers0() {
		final ResponseEntity<ArrayList> response = restTemplate.getForEntity(getUrl, ArrayList.class);
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

		var objectList = response.getBody();
		objectList.forEach(System.out::println);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void getUsers() {
		final ResponseEntity<User[]> response = restTemplate.getForEntity(getUrl, User[].class);
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
		HttpHeaders httpHeaders = restTemplate.headForHeaders(getUrl);
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
		HttpHeaders httpHeaders = restTemplate.headForHeaders(getUrl);
		HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);

		String response = restTemplate.postForObject(getUrl, request, String.class);
		System.out.println(response);
	}
}
