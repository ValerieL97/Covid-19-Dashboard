package com.example.covidtracker.controllers;

import com.example.covidtracker.models.ChartEntity;
import com.example.covidtracker.models.CountryCovidInfo;
import com.example.covidtracker.models.GlobalCovidInfo;
import com.example.covidtracker.services.CovidTrackerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class CovidTrackerController {

    private final CovidTrackerService covidTrackerService;

    public CovidTrackerController(CovidTrackerService covidTrackerService) {
        this.covidTrackerService = covidTrackerService;
    }

    //----------------------------------------Global Data--------------------------------------------------//
    @RequestMapping(value = "/globals", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<List<GlobalCovidInfo>> getGlobalInformation() {
        return new ResponseEntity<>(covidTrackerService.getGlobals(), HttpStatus.OK);
    }

    //data for dropdown
    @RequestMapping(value = "/countries-names", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<String>> getNames() {
        LinkedList<String> list = covidTrackerService.getCountriesNameList();
        list.addFirst("Global");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //get data for charts
    @RequestMapping(value = "/charts-global", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ChartEntity> getGlobalHistoricalInformation() {
        return new ResponseEntity<>(covidTrackerService.createChartEntity("Global"), HttpStatus.OK);
    }

    //---------------------------------------------Countries Data--------------------------------------------//

    //data for main table
    @RequestMapping(value = "/countries-all", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<CountryCovidInfo>> getCountries() {
        return new ResponseEntity<>(covidTrackerService.getCountriesDataList(), HttpStatus.OK);
    }

    //data for tables
    @RequestMapping(value = "/table-vaccinatedAtLeastOneDose", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<CountryCovidInfo>> getCountriesByVaccination() {
        return new ResponseEntity<>(covidTrackerService.getOrderCountriesByVaccinationData(), HttpStatus.OK);
    }


    @RequestMapping(value = "/countries-7daysCases", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<CountryCovidInfo>> getCountriesBy7daysCases() {
        return new ResponseEntity<>(covidTrackerService.getOrderCountriesBy7daysCases(), HttpStatus.OK);
    }

    @RequestMapping(value = "/countries-7daysDeaths", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<LinkedList<CountryCovidInfo>> getCountriesBy7daysDeaths() {
        return new ResponseEntity<>(covidTrackerService.getOrderCountriesBy7daysDeaths(), HttpStatus.OK);
    }


    //data for table by continent
    @RequestMapping(value = "/countries-all-by-continent", produces = { "application/json" }, method =RequestMethod.POST)
    public ResponseEntity<LinkedList<CountryCovidInfo>> getCountriesByContinent(@RequestBody String continent) {
        String continent1 = continent.substring(14,continent.length()-2);
        return new ResponseEntity<>(covidTrackerService.getCountriesByContinent(continent1), HttpStatus.OK);
    }

    //data for general data by country
    @RequestMapping(value = "/country", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<CountryCovidInfo> getCountry(@RequestBody String country) {
        String country1 = country.substring(12,country.length()-2);
        return new ResponseEntity<>(covidTrackerService.getCountry(country1), HttpStatus.OK);
    }

    //data for charts
    @RequestMapping(value = "/country-charts", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ChartEntity> getCountryHistoricalDeathsInformation(@RequestBody String country) {
        String country1 = country.substring(12,country.length()-2);
        return new ResponseEntity<>(covidTrackerService.createChartEntity(country1), HttpStatus.OK);
    }



}
