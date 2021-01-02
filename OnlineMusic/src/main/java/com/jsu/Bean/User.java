package com.jsu.Bean;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int authority;
    private String photo;
    public User(){super();}

    public User(int id, String name, String email, String password, int authority, String photo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
