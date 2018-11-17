package com.example.kudri.theauaraiy;

public class City {

    private long id;
    private String ruName;
    private String enName;

    public City() {}

    public City(long id, String ruName, String enName) {
        this.id = id;
        this.ruName = ruName;
        this.enName = enName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
