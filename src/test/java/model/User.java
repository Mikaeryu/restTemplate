package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
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

    public String getName() {
        return name;
    }
}

