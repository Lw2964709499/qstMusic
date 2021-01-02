package com.jsu.Bean;

public class Music {
    private int id;
    private String name;
    private String singerName;
    private int kid;
    private String kindName;
    private String playaddress;
    private int playnum;
    private int commnentNum;
    private String photo;
    public Music(){super();}
    public Music(int id, String name, String singerName, int kid,String kindName, String playaddress, int playnum,int commnentNum, String photo) {
        this.id = id;
        this.name = name;
        this.singerName = singerName;
        this.kid=kid;
        this.kindName = kindName;
        this.playaddress = playaddress;
        this.playnum=playnum;
        this.commnentNum = commnentNum;
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

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getPlayaddress() {
        return playaddress;
    }

    public void setPlayaddress(String description) {
        this.playaddress = description;
    }

    public int getPlaynum() {
        return playnum;
    }

    public void setPlaynum(int playnum) {
        this.playnum = playnum;
    }

    public int getCommnentNum() {
        return commnentNum;
    }

    public void setCommnentNum(int commnentNum) {
        this.commnentNum = commnentNum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }
}
