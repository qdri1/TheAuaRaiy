package com.example.kudri.theauaraiy;

import java.io.Serializable;

/**
 * Created by Kudri on 04.06.2018.
 */

public class Weather implements Serializable {

    private double lon;
    private double lat;

    private String weatherMain;
    private String weatherDesc;

    private int temp;
    private int humidity;

    private int windSpeed;
    private int windDeg;

    private long date;
    private String dateTxt;
    private long id;
    private String name;
    private String icon;

    public Weather(double lon, double lat, String weatherMain, String weatherDesc, int temp, int humidity, int windSpeed, int windDeg, long date, long id, String name, String icon) {
        this.lon = lon;
        this.lat = lat;
        this.weatherMain = weatherMain;
        this.weatherDesc = weatherDesc;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.date = date;
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public Weather(String weatherMain, String weatherDesc, String icon, int temp, int humidity, int windSpeed, long date, String dateTxt) {
        this.weatherMain = weatherMain;
        this.weatherDesc = weatherDesc;
        this.icon = icon;
        this.temp = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.date = date;
        this.dateTxt = dateTxt;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(int windDeg) {
        this.windDeg = windDeg;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDateTxt() {
        return dateTxt;
    }

    public void setDateTxt(String dateTxt) {
        this.dateTxt = dateTxt;
    }

}
