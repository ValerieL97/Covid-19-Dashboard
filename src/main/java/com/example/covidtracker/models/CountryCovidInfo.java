package com.example.covidtracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.LinkedHashMap;
import java.util.Map;

@RedisHash("CountriesCovidInfoR")
public class CountryCovidInfo {

    @Id
    private String name;
    private String continent;

    private Long totalCases;
    private Long todayCases;
    private Long last7daysCases;
    private Double percentageVaccinatedAtLeastOneDose;

    private Long totalDeaths;
    private Long todayDeaths;
    private Long last7daysDeaths;

    private Long totalRecovered;
    private Long todayRecovered;

    private Long active;

    private Long totalVaccineDosesAdministrated;
    private Long peopleVaccinatedAtLeastOneDose;
    private Long peopleFullyVaccinated;
    private Long peoplePartiallyVaccinated;
    private Long boosterDosesAdministrated;
    private Long population;


    public CountryCovidInfo() {
    }

    public CountryCovidInfo(String name, String continent, Long last7daysCases, Long last7daysDeaths) {
        this.name = name;
        this.continent = continent;
        this.last7daysCases = last7daysCases;
        this.last7daysDeaths = last7daysDeaths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Long getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(Long totalCases) {
        this.totalCases = totalCases;
    }

    public Long getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Long todayCases) {
        this.todayCases = todayCases;
    }

    public Long getLast7daysCases() {
        return last7daysCases;
    }

    public void setLast7daysCases(Long last7daysCases) {
        this.last7daysCases = last7daysCases;
    }

    public Long getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Long totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Long getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Long todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Long getLast7daysDeaths() {
        return last7daysDeaths;
    }

    public void setLast7daysDeaths(Long last7daysDeaths) {
        this.last7daysDeaths = last7daysDeaths;
    }

    public Long getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Long totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public Long getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Long todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Long getTotalVaccineDosesAdministrated() {
        return totalVaccineDosesAdministrated;
    }

    public void setTotalVaccineDosesAdministrated(Long totalVaccineDosesAdministrated) {
        this.totalVaccineDosesAdministrated = totalVaccineDosesAdministrated;
    }

    public Long getPeopleFullyVaccinated() {
        return peopleFullyVaccinated;
    }

    public void setPeopleFullyVaccinated(Long peopleFullyVaccinated) {
        this.peopleFullyVaccinated = peopleFullyVaccinated;
    }

    public Long getPeoplePartiallyVaccinated() {
        return peoplePartiallyVaccinated;
    }

    public void setPeoplePartiallyVaccinated(Long peoplePartiallyVaccinated) {
        this.peoplePartiallyVaccinated = peoplePartiallyVaccinated;
    }

    public Long getBoosterDosesAdministrated() {
        return boosterDosesAdministrated;
    }

    public void setBoosterDosesAdministrated(Long boosterDosesAdministrated) {
        this.boosterDosesAdministrated = boosterDosesAdministrated;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Long getPeopleVaccinatedAtLeastOneDose() {
        return peopleVaccinatedAtLeastOneDose;
    }

    public void setPeopleVaccinatedAtLeastOneDose(Long peopleVaccinatedAtLeastOneDose) {
        this.peopleVaccinatedAtLeastOneDose = peopleVaccinatedAtLeastOneDose;
    }

    public Double getPercentageVaccinatedAtLeastOneDose() {
        return percentageVaccinatedAtLeastOneDose;
    }

    public void setPercentageVaccinatedAtLeastOneDose(Double percentageVaccinatedAtLeastOneDose) {
        this.percentageVaccinatedAtLeastOneDose = percentageVaccinatedAtLeastOneDose;
    }
}
