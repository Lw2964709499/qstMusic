package com.jsu.Bean;

public class User_Music {
    private Music music;
    private int slId;
    public User_Music() {
    }

    public User_Music(Music music, int slId) {
        this.music = music;
        this.slId = slId;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public int getSlId() {
        return slId;
    }

    public void setSlId(int slId) {
        this.slId = slId;
    }
}
