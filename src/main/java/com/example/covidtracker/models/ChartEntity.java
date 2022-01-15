package com.example.covidtracker.models;

import java.util.LinkedList;

public class ChartEntity {

    //main chart
    private LinkedList<Long> deaths;
    private LinkedList<Long> cases;
    private LinkedList<String> keys;

    //total cases chart
    private Long activeCases;
    private Long recovered;
    private Long deathsTotal;

    //non infected chart
    private Long casesTotal;
    private Long population;

    //non vaccinated chart
    private Long vaccinatedAtLeastOneDoses;

    //vaccination chart
    private Long totalDosesAdministrated;
    private Long boosterDoses;
    private Long partiallyVaccinated;
    private Long fullyVaccinated;

    public ChartEntity() {
    }

    public LinkedList<Long> getDeaths() {
        return deaths;
    }

    public void setDeaths(LinkedList<Long> deaths) {
        this.deaths = deaths;
    }

    public LinkedList<Long> getCases() {
        return cases;
    }

    public void setCases(LinkedList<Long> cases) {
        this.cases = cases;
    }

    public LinkedList<String> getKeys() {
        return keys;
    }

    public void setKeys(LinkedList<String> keys) {
        this.keys = keys;
    }

    public Long getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(Long activeCases) {
        this.activeCases = activeCases;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public Long getDeathsTotal() {
        return deathsTotal;
    }

    public void setDeathsTotal(Long deathsTotal) {
        this.deathsTotal = deathsTotal;
    }

    public Long getCasesTotal() {
        return casesTotal;
    }

    public void setCasesTotal(Long casesTotal) {
        this.casesTotal = casesTotal;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getVaccinatedAtLeastOneDoses() {
        return vaccinatedAtLeastOneDoses;
    }

    public void setVaccinatedAtLeastOneDoses(Long vaccinatedAtLeastOneDoses) {
        this.vaccinatedAtLeastOneDoses = vaccinatedAtLeastOneDoses;
    }

    public Long getBoosterDoses() {
        return boosterDoses;
    }

    public void setBoosterDoses(Long boosterDoses) {
        this.boosterDoses = boosterDoses;
    }

    public Long getPartiallyVaccinated() {
        return partiallyVaccinated;
    }

    public void setPartiallyVaccinated(Long partiallyVaccinated) {
        this.partiallyVaccinated = partiallyVaccinated;
    }

    public Long getFullyVaccinated() {
        return fullyVaccinated;
    }

    public void setFullyVaccinated(Long fullyVaccinated) {
        this.fullyVaccinated = fullyVaccinated;
    }

    public Long getTotalDosesAdministrated() {
        return totalDosesAdministrated;
    }

    public void setTotalDosesAdministrated(Long totalDosesAdministrated) {
        this.totalDosesAdministrated = totalDosesAdministrated;
    }
}
