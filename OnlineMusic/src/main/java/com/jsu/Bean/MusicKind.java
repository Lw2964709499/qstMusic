package com.jsu.Bean;

public class MusicKind {
    private int kid;
    private String name;

    public MusicKind() {
    }

    public MusicKind(int kid, String name) {
        this.kid = kid;
        this.name = name;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
