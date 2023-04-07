package app.RestTemplate;

import app.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestTemplateApplication {

	private static final String URL = "http://94.198.50.185:7081/api/users";

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(RestTemplateApplication.class, args);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = restTemplate.headForHeaders(URL);

		String part1 = postRequest(httpHeaders, restTemplate);

		System.out.println(part1);
		applicationContext.close();
	}

	static String postRequest(HttpHeaders httpHeaders, RestTemplate restTemplate) {
		var userToSave = new User(3, "James", "Brown", 25);
		String json = userToSave.getJson();
		HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);

		return restTemplate.postForObject(URL, request, String.class);
	}
}
