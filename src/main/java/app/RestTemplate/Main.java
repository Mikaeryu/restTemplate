package app.RestTemplate;

import app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        String url = "http://94.198.50.185:7081/api/users/";
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(3, "James", "Brown", 88);
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String code = "";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String jsessionid = response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0);

        headers.add(HttpHeaders.COOKIE, jsessionid);
        HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(user), headers);
        response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        code += response.getBody();
        System.out.println("1-_? _____?____ ________: " + response.getBody());

        User user2 = new User(3, "Thomas", "Shelby", 88);

        entity = new HttpEntity<>(mapper.writeValueAsString(user2), headers);
        response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
        code += response.getBody();
        System.out.println("2-_? _____?____ ________: " + response.getBody());

        entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url + user2.getId(), HttpMethod.DELETE, entity, String.class);
        code += response.getBody();
        System.out.println("3-_? _____?____ ________: " + response.getBody());

        System.out.println("______: " + code);
    }
}
