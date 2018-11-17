package com.example.kudri.theauaraiy;

import java.util.ArrayList;
import java.util.List;

public class CitiesService {

    private static List<City> getListOfCitiesKZ() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(1526384, "Алматы", "Almaty"));
        cities.add(new City(1526273, "Астана", "Astana"));
        cities.add(new City(610529, "Атырау", "Atyrau"));
        cities.add(new City(1522203, "Кокшетау", "Kokshetau"));
        cities.add(new City(1520172, "Петропавловск", "Petropavlovsk"));
        cities.add(new City(1520240, "Павлодар", "Pavlodar"));
        cities.add(new City(1519922, "Кызылорда", "Qyzylorda"));
        cities.add(new City(1519948, "Капчагай", "Qapshaghay"));
        cities.add(new City(1519422, "Семей", "Semey"));
        cities.add(new City(1518262, "Темиртау", "Temirtau"));
        cities.add(new City(1516905, "Тараз", "Taraz"));
        cities.add(new City(1517945, "Туркистан", "Turkestan"));
        cities.add(new City(1519928, "Костанай", "Qostanay"));
        cities.add(new City(610611, "Актобе", "Aqtobe"));
        cities.add(new City(1518543, "Талдыкорган", "Taldyqorghan"));
        cities.add(new City(1518980, "Шымкент", "Shymkent"));
        cities.add(new City(610612, "Актау", "Aktau"));
        cities.add(new City(608668, "Орал", "Oral"));
        return cities;
    }

    private static List<City> getListOfCitiesRU() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(524901, "Москва", "Moscow"));
        cities.add(new City(703448, "Киев", "Kiev"));
        cities.add(new City(491422, "Сочи", "Sochi"));
        cities.add(new City(1486209, "Екатеринбург", "Yekaterinburg"));
        cities.add(new City(499099, "Самара", "Samara"));
        return cities;
    }

    private static List<City> getListOfCitiesOTH() {
        List<City> cities = new ArrayList<>();
        cities.add(new City(1512569, "Ташкент", "Tashkent"));
        cities.add(new City(1216265, "Самарканд", "Samarqand"));
        return cities;
    }

    public static List<String> getEnNames(int country) {
        List<String> names = new ArrayList<>();
        List<City> cities;
        if (country == 0) {
            cities = getListOfCitiesKZ();
        } else if (country == 1) {
            cities = getListOfCitiesRU();
        } else {
            cities = getListOfCitiesOTH();
        }

        for (City c : cities) {
            names.add(c.getEnName());
        }

        return names;
    }

    public static String getRuName(int country, String enName) {
        List<City> cities;
        if (country == 0) {
            cities = getListOfCitiesKZ();
        } else if (country == 1) {
            cities = getListOfCitiesRU();
        } else {
            cities = getListOfCitiesOTH();
        }

        for (City c : cities) {
            if (enName.equals(c.getEnName())) {
                return c.getRuName();
            }
        }

        return null;
    }

    public static String getIds(int country) {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        List<City> cities;

        if (country == 0) {
            cities = getListOfCitiesKZ();
        } else if (country == 1) {
            cities = getListOfCitiesRU();
        } else {
            cities = getListOfCitiesOTH();
        }

        for (City c : cities) {
            counter++;
            builder.append(c.getId());
            if (counter == cities.size()) {
                break;
            }
            builder.append(",");
        }
        return builder.toString();
    }

}
