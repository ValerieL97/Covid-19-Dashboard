package com.example.covidtracker.models.utilis;



import com.example.covidtracker.models.obj.CountryData;
import com.example.covidtracker.models.obj.CountryHistoricalData;
import org.apache.commons.csv.CSVRecord;

import java.util.Collection;


public final class CovidDataUtils {

    public static CountryData findByName(Collection<CountryData> listCountryData, String name) {
        return FindUtils.findByProperty(listCountryData, countryData -> name.equals(countryData.getCountry()));
    }

    public static CountryHistoricalData findByCountry(Collection<CountryHistoricalData> listHistoricalData, String name) {
        return FindUtils.findByProperty(listHistoricalData, country -> name.equals(country.getCountry()));
    }

    public static CountryHistoricalData findByProvince(Collection<CountryHistoricalData> listHistoricalData, String name) {
        return FindUtils.findByProperty(listHistoricalData, countryHistoricalData -> name.equals(countryHistoricalData.getProvince()));
    }

    public static CSVRecord findByCountryName(Collection<CSVRecord> vaccinationData, String name) {
        return FindUtils.findByProperty(vaccinationData, countryVaccinationData-> name.equals(countryVaccinationData.get("name")));
    }

}
