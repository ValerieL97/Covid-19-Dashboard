package com.example.covidtracker.repositories;

import com.example.covidtracker.models.CountryCovidInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;
import java.util.List;

public interface CountryCovidInfoRepo extends CrudRepository<CountryCovidInfo,String> {

    LinkedList<CountryCovidInfo> findAll();

    LinkedList<CountryCovidInfo> findAllByContinent(String continent);
}
