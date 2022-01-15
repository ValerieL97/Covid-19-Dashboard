package com.example.covidtracker.repositories;


import com.example.covidtracker.models.GlobalCovidInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GlobalCovidInfoRepo extends CrudRepository<GlobalCovidInfo,String> {

    List<GlobalCovidInfo> findAll();
}
