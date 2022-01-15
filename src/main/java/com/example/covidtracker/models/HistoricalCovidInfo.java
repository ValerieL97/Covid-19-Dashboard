package com.example.covidtracker.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Indexed;


@RedisHash("HistoricalCovidInformationR")
@Indexed
public class HistoricalCovidInfo implements Comparable<HistoricalCovidInfo>{

    @Id
    private String id;
    private String country;
    private Integer position;
    private String list;
    private String date;
    private Long value;

    public HistoricalCovidInfo() {
    }

    public HistoricalCovidInfo(String country, Integer position, String list, String date, Long value) {
        this.country = country;
        this.position = position;
        this.list = list;
        this.date = date;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int compareTo(HistoricalCovidInfo o) {
        return this.getPosition().compareTo(o.getPosition());
    }
}
