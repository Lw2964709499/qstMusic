package com.jsu.Bean;

public class Comment {
    private int id;
    private String description;
    private int music_id;
    private String userName;

    public Comment() {
    }

    public Comment(int id, String description, int music_id, String userName) {
        this.id = id;
        this.description = description;
        this.music_id = music_id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
