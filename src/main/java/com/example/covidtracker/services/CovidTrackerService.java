package com.example.covidtracker.services;

import com.example.covidtracker.models.ChartEntity;
import com.example.covidtracker.models.CountryCovidInfo;
import com.example.covidtracker.models.GlobalCovidInfo;
import com.example.covidtracker.models.HistoricalCovidInfo;
import com.example.covidtracker.repositories.CountryCovidInfoRepo;
import com.example.covidtracker.repositories.GlobalCovidInfoRepo;
import com.example.covidtracker.repositories.HistoricalCovidInfoRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CovidTrackerService {

    private final GlobalCovidInfoRepo globalCovidInfoRepo;
    private final CountryCovidInfoRepo countryCovidInfoRepo;
    private final HistoricalCovidInfoRepo historicalCovidInfoRepo;

    public CovidTrackerService(GlobalCovidInfoRepo globalCovidInfoRepo,
                               CountryCovidInfoRepo countryCovidInfoRepo,
                               HistoricalCovidInfoRepo historicalCovidInfoRepo) {
        this.globalCovidInfoRepo = globalCovidInfoRepo;
        this.countryCovidInfoRepo = countryCovidInfoRepo;
        this.historicalCovidInfoRepo = historicalCovidInfoRepo;
    }

    //----------------------------General data---------------------------//
    // return a global covid info
    public List<GlobalCovidInfo> getGlobals() {
        return globalCovidInfoRepo.findAll();
    }

    // return a sorted list of countries for dropdown
    public LinkedList<String> getCountriesNameList() {
        LinkedList<CountryCovidInfo> list = countryCovidInfoRepo.findAll();
        LinkedList<String> namesList = new LinkedList<>();
        for(CountryCovidInfo country : list) {
            namesList.add(country.getName());
        }
        Collections.sort(namesList);
        return namesList;
    }

    // return a covid info about a country
    public CountryCovidInfo getCountry(String country) {
        return countryCovidInfoRepo.findById(country).get();
    }

    //----------------------------------Charts-----------------------------------------//


    //create a chart entity with data for charts
    public ChartEntity createChartEntity(String country) {
        ChartEntity chartEntity = new ChartEntity();

        if(country.equals("Global")){
            GlobalCovidInfo global = globalCovidInfoRepo.findAll().get(0);
            LinkedHashMap<String,Long> cases = getHistoricalData("cases","Global");
            LinkedHashMap<String,Long> deaths = getHistoricalData("deaths","Global");
            chartEntity.setDeaths(new LinkedList<>(deaths.values()));
            chartEntity.setCases(new LinkedList<>(cases.values()));
            chartEntity.setKeys(new LinkedList<>(cases.keySet()));
            chartEntity.setActiveCases(global.getActive());
            chartEntity.setPopulation(global.getPopulation());
            chartEntity.setBoosterDoses(global.getBoosterDosesAdministrated());
            chartEntity.setCasesTotal(global.getTotalCases());
            chartEntity.setDeathsTotal(global.getTotalDeaths());
            chartEntity.setFullyVaccinated(global.getPeopleFullyVaccinated());
            chartEntity.setPartiallyVaccinated(global.getPeoplePartiallyVaccinated());
            chartEntity.setRecovered(global.getTotalRecovered());
            chartEntity.setVaccinatedAtLeastOneDoses(global.getPeopleFullyVaccinated() +
                    global.getPeoplePartiallyVaccinated());
            chartEntity.setTotalDosesAdministrated(global.getTotalVaccineDosesAdministrated());

        } else {
            CountryCovidInfo countryInfo = countryCovidInfoRepo.findById(country).get();
            LinkedHashMap<String,Long> cases = getHistoricalData("cases",country);
            LinkedHashMap<String,Long> deaths = getHistoricalData("deaths",country);
            chartEntity.setDeaths(new LinkedList<>(deaths.values()));
            chartEntity.setCases(new LinkedList<>(cases.values()));
            chartEntity.setKeys(new LinkedList<>(cases.keySet()));
            chartEntity.setActiveCases(countryInfo.getActive());
            chartEntity.setPopulation(countryInfo.getPopulation());
            chartEntity.setBoosterDoses(countryInfo.getBoosterDosesAdministrated());
            chartEntity.setCasesTotal(countryInfo.getTotalCases());
            chartEntity.setDeathsTotal(countryInfo.getTotalDeaths());
            chartEntity.setFullyVaccinated(countryInfo.getPeopleFullyVaccinated());
            chartEntity.setPartiallyVaccinated(countryInfo.getPeoplePartiallyVaccinated());
            chartEntity.setRecovered(countryInfo.getTotalRecovered());
            chartEntity.setVaccinatedAtLeastOneDoses(countryInfo.getPeopleFullyVaccinated() +
                    countryInfo.getPeoplePartiallyVaccinated());
            chartEntity.setTotalDosesAdministrated(countryInfo.getTotalVaccineDosesAdministrated());

        }

        return chartEntity;
    }


    //create maps with dates and numbers of cases or deaths from historicalData objects
    public LinkedHashMap<String,Long> getHistoricalData(String list, String country) {
        List<HistoricalCovidInfo> historicalCovidInfosList = historicalCovidInfoRepo.findAll();
        List<HistoricalCovidInfo> list3 = new LinkedList<>();

        for(HistoricalCovidInfo historicalCovidInfo : historicalCovidInfosList) {
            if(historicalCovidInfo.getCountry().equals(country) && historicalCovidInfo.getList().equals(list)) {
                list3.add(historicalCovidInfo);
            }
        }

        Collections.sort(list3);
        LinkedHashMap<String,Long> map = new LinkedHashMap<>();

        for(HistoricalCovidInfo historicalCovidInfo : list3) {
            map.put(historicalCovidInfo.getDate(),historicalCovidInfo.getValue());
        }

        return map;

    }

    //----------------------------------Tables------------------------------------------//

    public LinkedList<CountryCovidInfo> getCountriesDataList() {
        LinkedList<CountryCovidInfo> list = countryCovidInfoRepo.findAll();
        Collections.sort(list, new Comparator<CountryCovidInfo>() {
            @Override
            public int compare(CountryCovidInfo o1, CountryCovidInfo o2) {
                return o1.getTotalCases().compareTo(o2.getTotalCases());
            }

        });
        Collections.reverse(list);
        return list;
    }

    // return a list of countries objects by continent
    public LinkedList<CountryCovidInfo> getCountriesByContinent(String continent) {
        LinkedList<CountryCovidInfo> list = countryCovidInfoRepo.findAll();
        LinkedList<CountryCovidInfo> list1 = new LinkedList<>();
        for(CountryCovidInfo country : list) {
            if(country.getContinent().toLowerCase().equals(continent.toLowerCase())) {
                list1.add(country);
            }
        }
        Collections.sort(list1, new Comparator<CountryCovidInfo>() {
            @Override
            public int compare(CountryCovidInfo o1, CountryCovidInfo o2) {
                return o1.getTotalCases().compareTo(o2.getTotalCases());
            }

        });
        Collections.reverse(list1);
        return list1;
    }

    // return a list of countries objects ordered by last 7 days cases
    public LinkedList<CountryCovidInfo> getOrderCountriesBy7daysCases() {
        LinkedList<CountryCovidInfo> countriesList = countryCovidInfoRepo.findAll();
        LinkedList<CountryCovidInfo> countries = new LinkedList<>();
        Collections.sort(countriesList, new Comparator<CountryCovidInfo>() {
            @Override
            public int compare(CountryCovidInfo o1, CountryCovidInfo o2) {
                return o1.getLast7daysCases().compareTo(o2.getLast7daysCases());
            }

        });
        Collections.reverse(countriesList);

        for(int i = 0; i < 10; i++) {
            countries.add(countriesList.get(i));
        }


        return countries;
    }

    // return a list of countries objects ordered by confirmed last 7 days deaths
    public LinkedList<CountryCovidInfo> getOrderCountriesBy7daysDeaths() {
        LinkedList<CountryCovidInfo> countriesList = countryCovidInfoRepo.findAll();
        LinkedList<CountryCovidInfo> countries = new LinkedList<>();
        Collections.sort(countriesList, new Comparator<CountryCovidInfo>() {
            @Override
            public int compare(CountryCovidInfo o1, CountryCovidInfo o2) {
                return o1.getLast7daysDeaths().compareTo(o2.getLast7daysDeaths());
            }

        });
        Collections.reverse(countriesList);

        for(int i = 0; i < 10; i++) {
            countries.add(countriesList.get(i));
        }

        return countries;
    }

    // return a list of countries objects ordered by vaccination data
    public LinkedList<CountryCovidInfo> getOrderCountriesByVaccinationData() {
        LinkedList<CountryCovidInfo> countriesList = countryCovidInfoRepo.findAll();
        LinkedList<CountryCovidInfo> countries = new LinkedList<>();
        Collections.sort(countriesList, new Comparator<CountryCovidInfo>() {
            @Override
            public int compare(CountryCovidInfo o1, CountryCovidInfo o2) {
                Long num1 = new Long(0);
                if(!Objects.isNull(o1.getPeopleVaccinatedAtLeastOneDose())) {
                    num1 = o1.getPeopleVaccinatedAtLeastOneDose();
                }
                Long num2 = new Long(0);
                if(!Objects.isNull(o2.getPeopleVaccinatedAtLeastOneDose())) {
                    num2 = o2.getPeopleVaccinatedAtLeastOneDose();
                }
                return num1.compareTo(num2);
            }

        });
        Collections.reverse(countriesList);
        for(int i = 0; i < 10; i++) {
            countries.add(countriesList.get(i));
        }

        return countries;
    }

}
