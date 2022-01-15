package com.example.covidtracker.repositories;

import com.example.covidtracker.models.HistoricalCovidInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoricalCovidInfoRepo extends CrudRepository<HistoricalCovidInfo,String> {

    List<HistoricalCovidInfo> findByCountryAndList(String list,String country);
    void deleteAllByCountry(String country);
    List<HistoricalCovidInfo> findAll();
}
