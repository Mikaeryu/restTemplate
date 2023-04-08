package app.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Data
public class User {
    private final long id;
    private final String name;
    private final String lastName;
    private final int age;

    @JsonCreator
    public User(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("age") int age) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    @SneakyThrows
    public String getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("lastName", lastName);
        jsonObject.put("age", age);

        return jsonObject.toString();
    }
}

