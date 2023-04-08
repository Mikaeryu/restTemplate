package app.RestTemplate;

import app.model.User;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

@SpringBootApplication
public class RestTemplateApplication {

	private static final String URL = "http://94.198.50.185:7081/api/users";
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(RestTemplateApplication.class, args);
		RestTemplate restTemplate = new RestTemplate();

		String jsessionid = getUsers(restTemplate);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add(HttpHeaders.COOKIE, jsessionid);

		String part1 = postRequest(headers, restTemplate);
		String part2 = putRequest(headers, restTemplate);
		String part3 = deleteRequest(headers, restTemplate);
		String fullMessage = part1 + part2 + part3;

		LOGGER.info(fullMessage);

		applicationContext.close();
	}

	static String  getUsers(RestTemplate restTemplate) {
		ResponseEntity<User[]> response = restTemplate.getForEntity(URL, User[].class);
		User[] body = Objects.requireNonNull(response.getBody());
		var objectList = Arrays.asList(body);
		objectList.forEach(o -> LOGGER.info(o.toString()));

		return response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);
	}

	@SneakyThrows
	static String postRequest(HttpHeaders headers, RestTemplate restTemplate) {
		var user = new User(3, "James", "Brown", 25);
		String json = user.getJson();
		HttpEntity<String> postRequest = new HttpEntity<>(json, headers);

		return restTemplate.postForObject(URL, postRequest, String.class);
	}

	static String putRequest(HttpHeaders headers, RestTemplate restTemplate) {
		var userUpdate = new User(3, "Thomas", "Shelby", 25);
		String json = userUpdate.getJson();
		HttpEntity<String> putRequest = new HttpEntity<>(json, headers);

		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, putRequest, String.class);

		return response.getBody();
	}

	static String deleteRequest(HttpHeaders headers, RestTemplate restTemplate) {
		String deleteUrl =  URL + "/3";
		HttpEntity<String> deleteRequest = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteRequest, String.class);

		return response.getBody();
	}
}
