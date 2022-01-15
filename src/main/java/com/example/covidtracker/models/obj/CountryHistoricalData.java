package com.example.covidtracker.models.obj;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CountryHistoricalData {

    private String country;
    private String province;
    private Map<String, LinkedHashMap<String, Long>> timeline;


    public CountryHistoricalData() {
    }



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Map<String, LinkedHashMap<String, Long>> getTimeline() {
        return timeline;
    }

    public void setTimeline(Map<String, LinkedHashMap<String, Long>> timeline) {
        this.timeline = timeline;
    }
}