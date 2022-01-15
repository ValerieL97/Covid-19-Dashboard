package com.example.covidtracker.models.obj;

import java.util.LinkedHashMap;


public class HistoricalData {

    LinkedHashMap<String,Long> cases;
    LinkedHashMap<String,Long> deaths;
    LinkedHashMap<String,Long> recovered;

    public HistoricalData() {
    }

    public HistoricalData(LinkedHashMap<String, Long> cases,
                          LinkedHashMap<String, Long> deaths, LinkedHashMap<String, Long> recovered) {
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public LinkedHashMap<String, Long> getCases() {
        return cases;
    }

    public void setCases(LinkedHashMap<String, Long> cases) {
        this.cases = cases;
    }

    public LinkedHashMap<String, Long> getDeaths() {
        return deaths;
    }

    public void setDeaths(LinkedHashMap<String, Long> deaths) {
        this.deaths = deaths;
    }

    public LinkedHashMap<String, Long> getRecovered() {
        return recovered;
    }

    public void setRecovered(LinkedHashMap<String, Long> recovered) {
        this.recovered = recovered;
    }
}
