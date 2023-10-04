package com.example.loadjson;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String location;

    User(Integer _id,
         String _name,
         String _email,
         String _location) {
        id = _id; name = _name; email = _email; location = _location;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
