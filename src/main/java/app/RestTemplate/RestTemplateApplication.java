package app.RestTemplate;

import app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
@SpringBootApplication
public class RestTemplateApplication {

	private static final String URL = "http://94.198.50.185:7081/api/users";
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(RestTemplateApplication.class, args);
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getUsers(restTemplate);

		String part1 = postRequest(headers, restTemplate);

		LOGGER.info(part1);
		applicationContext.close();
	}

	static HttpHeaders getUsers(RestTemplate restTemplate) {
		ResponseEntity<User[]> response = restTemplate.getForEntity(URL, User[].class);
		HttpHeaders headers = response.getHeaders();

		var objectList = Arrays.asList(response.getBody());
		objectList.forEach(o -> LOGGER.info(o.toString()));

		return headers;
	}

	static String postRequest(HttpHeaders headers, RestTemplate restTemplate) {
		var user = new User(3, "James", "Brown", 25);
		String json = user.getJson();
		HttpEntity<String> request = new HttpEntity<>(json, headers);

		return restTemplate.postForObject(URL, request, String.class);
	}

	static void putRequest(HttpHeaders headers, RestTemplate restTemplate) {

	}
}
