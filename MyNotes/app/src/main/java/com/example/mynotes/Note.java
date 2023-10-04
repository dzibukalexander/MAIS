package com.example.mynotes;

public class Note {
    private Integer id;
    private String description;

    public Note(Integer _id,
         String _description) {
         id = _id; description = _description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
