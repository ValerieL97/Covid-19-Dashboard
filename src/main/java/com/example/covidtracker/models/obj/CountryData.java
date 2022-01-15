package com.example.covidtracker.models.obj;

import java.util.List;
import java.util.Map;

public class CountryData {

    private String updated;
    private String country;
    private Map<String,String> countryInfo;
    private Long cases;
    private Long todayCases;
    private Long deaths;
    private Long todayDeaths;
    private Long recovered;
    private Long todayRecovered;
    private Long active;
    private Long critical;
    private Double casesPerOneMillion;
    private Double deathsPerOneMillion;
    private Long tests;
    private Double testsPerOneMillion;
    private Long population;
    private String continent;
    private Double oneCasePerPeople;
    private Double oneTestPerPeople;
    private Double activePerPeople;
    private Double recoveredPerOneMillion;
    private Double criticalPerOneMillion;

    public CountryData() {
    }

    public CountryData(String updated, String country, Map<String, String> countryInfo, Long cases,
                       Long todayCases, Long deaths, Long todayDeaths, Long recovered, Long todayRecovered,
                       Long active, Long critical, Double casesPerOneMillion, Double deathsPerOneMillion,
                       Long tests, Double testsPerOneMillion, Long population, String continent,
                       Double oneCasePerPeople, Double oneTestPerPeople,
                       Double activePerPeople, Double recoveredPerOneMillion, Double criticalPerOneMillion) {
        this.updated = updated;
        this.country = country;
        this.countryInfo = countryInfo;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.critical = critical;
        this.casesPerOneMillion = casesPerOneMillion;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.tests = tests;
        this.testsPerOneMillion = testsPerOneMillion;
        this.population = population;
        this.continent = continent;
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneTestPerPeople = oneTestPerPeople;
        this.activePerPeople = activePerPeople;
        this.recoveredPerOneMillion = recoveredPerOneMillion;
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Map<String, String> getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(Map<String, String> countryInfo) {
        this.countryInfo = countryInfo;
    }

    public Long getCases() {
        return cases;
    }

    public void setCases(Long cases) {
        this.cases = cases;
    }

    public Long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Long todayCases) {
        this.todayCases = todayCases;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Long todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Long getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Long todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Long getCritical() {
        return critical;
    }

    public void setCritical(Long critical) {
        this.critical = critical;
    }

    public Long getTests() {
        return tests;
    }

    public void setTests(Long tests) {
        this.tests = tests;
    }



    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Double casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public Double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Double deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Double testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Double getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(Double oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public Double getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(Double oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public Double getActivePerPeople() {
        return activePerPeople;
    }

    public void setActivePerPeople(Double activePerPeople) {
        this.activePerPeople = activePerPeople;
    }

    public Double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(Double recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public Double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(Double criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "updated='" + updated + '\'' +
                ", country='" + country + '\'' +
                ", countryInfo=" + countryInfo +
                ", cases=" + cases +
                ", todayCases=" + todayCases +
                ", deaths=" + deaths +
                ", todayDeaths=" + todayDeaths +
                ", recovered=" + recovered +
                ", todayRecovered=" + todayRecovered +
                ", active=" + active +
                ", critical=" + critical +
                ", casesPerOneMillion=" + casesPerOneMillion +
                ", deathsPerOneMillion=" + deathsPerOneMillion +
                ", tests=" + tests +
                ", testsPerOneMillion=" + testsPerOneMillion +
                ", population=" + population +
                ", continent='" + continent + '\'' +
                ", oneCasePerPeople=" + oneCasePerPeople +
                ", oneTestPerPeople=" + oneTestPerPeople +
                ", activePerPeople=" + activePerPeople +
                ", recoveredPerOneMillion=" + recoveredPerOneMillion +
                ", criticalPerOneMillion=" + criticalPerOneMillion +
                '}';
    }
}

