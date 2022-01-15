package com.example.covidtracker.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.LinkedHashMap;
import java.util.Map;

@RedisHash("GlobalCovidInformationR")
public class GlobalCovidInfo {

    @Id
    private Long update;

    private Long totalCases;
    private Long todayCases;
    private Long last7daysCases;
    private Long totalDeaths;
    private Long todayDeaths;
    private Long last7daysDeaths;
    private Long totalRecovered;
    private Long todayRecovered;
    private Long active;
    private Long totalVaccineDosesAdministrated;
    private Long peopleFullyVaccinated;
    private Long peoplePartiallyVaccinated;
    private Long boosterDosesAdministrated;
    private Long population;


    public GlobalCovidInfo() {
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
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

    public Long getLast7daysCases() {
        return last7daysCases;
    }

    public void setLast7daysCases(Long last7daysCases) {
        this.last7daysCases = last7daysCases;
    }

    public Long getLast7daysDeaths() {
        return last7daysDeaths;
    }

    public void setLast7daysDeaths(Long last7daysDeaths) {
        this.last7daysDeaths = last7daysDeaths;
    }
}
