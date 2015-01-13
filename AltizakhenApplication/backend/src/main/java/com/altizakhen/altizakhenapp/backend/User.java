package com.altizakhen.altizakhenapp.backend;

/**
 * Created by Omar on 1/10/15.
 */
public class User {

    private String id;
    private String name;
    private String facebookId;


    public User(String id, String name, String facebookId) {
        this.id = id;
        this.name = name;
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}