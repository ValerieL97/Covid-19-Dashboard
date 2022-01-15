package com.example.covidtracker.services;

import com.example.covidtracker.models.CountryCovidInfo;
import com.example.covidtracker.models.GlobalCovidInfo;
import com.example.covidtracker.models.HistoricalCovidInfo;
import com.example.covidtracker.models.obj.CountryData;
import com.example.covidtracker.models.obj.CountryHistoricalData;
import com.example.covidtracker.models.obj.GlobalData;
import com.example.covidtracker.models.obj.HistoricalData;
import com.example.covidtracker.models.utilis.CovidDataUtils;
import com.example.covidtracker.repositories.CountryCovidInfoRepo;
import com.example.covidtracker.repositories.GlobalCovidInfoRepo;
import com.example.covidtracker.repositories.HistoricalCovidInfoRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CovidInfoService {

    private static final String VACCINE_DATA = "https://raw.githubusercontent.com/BloombergGraphics/covid-vaccine-tracker-data/master/data/current-global.csv";
    private static final String GLOBAL_DATA = "https://disease.sh/v3/covid-19/all";
    private static final String HISTORICAL_GLOBAL_DATA = "https://disease.sh/v3/covid-19/historical/all?lastdays=all";
    private static final String COUNTRY_DATA = "https://disease.sh/v3/covid-19/countries";
    private static final String HISTORICAL_COUNTRY_DATA ="https://disease.sh/v3/covid-19/historical?lastdays=all";

    private static final RestTemplate restTemplate = new RestTemplate();

    private final GlobalCovidInfoRepo globalCovidInfoRepo;
    private final CountryCovidInfoRepo countryCovidInfoRepo;
    private final HistoricalCovidInfoRepo historicalCovidInfoRepo;

    public CovidInfoService(GlobalCovidInfoRepo globalCovidInfoRepo, CountryCovidInfoRepo countryCovidInfoRepo, HistoricalCovidInfoRepo historicalCovidInfoRepo) {
        this.globalCovidInfoRepo = globalCovidInfoRepo;
        this.countryCovidInfoRepo = countryCovidInfoRepo;
        this.historicalCovidInfoRepo = historicalCovidInfoRepo;
    }


    @Scheduled(fixedRate = 6 * 1000 * 60 * 60)
    private void updateCovidInfo() throws IOException, InterruptedException {
        historicalCovidInfoRepo.deleteAll();
        globalCovidInfoRepo.deleteAll();
        countryCovidInfoRepo.deleteAll();
        createCountriesCovidInfo();
        createNewGlobalCovidInfo();
    }


    //-------------------------------Save Global Info----------------------------//

    private void createNewGlobalCovidInfo() throws IOException, InterruptedException {

        GlobalData globalData = restTemplate.getForObject(GLOBAL_DATA, GlobalData.class);
        HistoricalData historicalData = restTemplate.getForObject(HISTORICAL_GLOBAL_DATA,HistoricalData.class);
        Map<String,Long> vaccinationData = getVaccinationGlobalData();

        LinkedHashMap<String,Long> cases = historicalData.getCases();
        LinkedHashMap<String,Long> deaths = historicalData.getDeaths();


        saveHistoricalData("Global", cases, "cases");
        saveHistoricalData("Global", deaths, "deaths");


        GlobalCovidInfo globalCovidInfo = new GlobalCovidInfo();

        globalCovidInfo.setUpdate(globalCovidInfo.getUpdate());
        globalCovidInfo.setTotalCases(globalData.getCases());
        globalCovidInfo.setTotalDeaths(globalData.getDeaths());
        globalCovidInfo.setTotalRecovered(globalData.getRecovered());
        globalCovidInfo.setTodayCases(globalData.getTodayCases());
        globalCovidInfo.setTodayDeaths(globalData.getTodayDeaths());
        globalCovidInfo.setTodayRecovered(globalData.getTodayRecovered());
        globalCovidInfo.setActive(globalData.getActive());
        globalCovidInfo.setPopulation(globalData.getPopulation());
        globalCovidInfo.setPeopleFullyVaccinated(vaccinationData.get("peopleFullyVaccinated"));
        globalCovidInfo.setPeoplePartiallyVaccinated(vaccinationData.get("peoplePartiallyVaccinated"));
        globalCovidInfo.setTotalVaccineDosesAdministrated(vaccinationData.get("totalVaccineDosesAdministrated"));
        globalCovidInfo.setBoosterDosesAdministrated(vaccinationData.get("boosterDosesAdministrated"));

        globalCovidInfo.setLast7daysCases(calculateTotal7DaysCases(cases));
        globalCovidInfo.setLast7daysDeaths(calculateTotal7DaysCases(deaths));

        globalCovidInfoRepo.save(globalCovidInfo);

    }

    private Map<String,Long> getVaccinationGlobalData() throws IOException, InterruptedException {

        List<CSVRecord> records = getCountriesVaccinationData();

        long totalVaccineDosesAdministrated = 0l;
        long peopleFullyVaccinated = 0l;
        long peopleVaccinated = 0l;
        long boosterDosesAdministrated = 0l;

        for (CSVRecord record : records) {

            long totalDosesAmd = record.get("dosesAdministered").length() > 0 ? Long.parseLong(record.get("dosesAdministered")) :0l;
            totalVaccineDosesAdministrated += totalDosesAmd;

            long completedVaccinated = record.get("completedVaccination").length() > 0 ? Long.parseLong(record.get("completedVaccination")) : 0l;
            peopleFullyVaccinated += completedVaccinated;

            long peopleV = record.get("peopleVaccinated").length() > 0 ? Long.parseLong(record.get("peopleVaccinated")):0l;
            peopleVaccinated += peopleV;

            long boosterDoses = record.get("boosterDosesAdministered").length() > 0 ? Long.parseLong(record.get("boosterDosesAdministered")) : 0l;
            if (record.get("boosterDosesAdministered").length() > 0) {
                boosterDosesAdministrated += boosterDoses;
            }
        }

        Map<String,Long> map = new HashMap<>();
        map.put("totalVaccineDosesAdministrated",totalVaccineDosesAdministrated);
        map.put("peopleFullyVaccinated", peopleFullyVaccinated);
        map.put("peoplePartiallyVaccinated",peopleVaccinated - peopleFullyVaccinated);
        map.put("boosterDosesAdministrated",boosterDosesAdministrated);

        return map;
    }



    //-------------------------------Save Countries Info------------------------//

    private void createCountriesCovidInfo() throws IOException, InterruptedException {
        ResponseEntity<List<CountryData>> countriesData = restTemplate.exchange(COUNTRY_DATA, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CountryData>>() {});
        List<CountryData> countryDataList = removeCountriesData(countriesData.getBody());

        ResponseEntity<List<CountryHistoricalData>> countriesHistoricalData= restTemplate.exchange(HISTORICAL_COUNTRY_DATA, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<CountryHistoricalData>>() {});
        List<CountryHistoricalData> countryHistoricalDataList = removeCountriesHistoricalData(countriesHistoricalData.getBody());

        List<CSVRecord> vaccinationInfo = getCountriesVaccinationData();

        for(CountryData countryData : countryDataList) {
            String country = countryData.getCountry();

            CountryCovidInfo countryCovidInfo = new CountryCovidInfo();
            countryCovidInfo.setName(country);
            countryCovidInfo.setContinent(countryData.getContinent());
            countryCovidInfo.setTodayCases(countryData.getTodayCases());
            countryCovidInfo.setTodayDeaths(countryData.getTodayDeaths());
            countryCovidInfo.setTodayRecovered(countryData.getTodayRecovered());
            countryCovidInfo.setTotalCases(countryData.getCases());
            countryCovidInfo.setTotalDeaths(countryData.getDeaths());
            countryCovidInfo.setTotalRecovered(countryData.getRecovered());
            countryCovidInfo.setPopulation(countryData.getPopulation());
            countryCovidInfo.setActive(countryData.getActive());

            CountryHistoricalData countryHistoricalData = CovidDataUtils.findByCountry(countryHistoricalDataList,country);
            LinkedHashMap<String,Long> cases = countryHistoricalData.getTimeline().get("cases");
            LinkedHashMap<String,Long> deaths = countryHistoricalData.getTimeline().get("deaths");

            saveHistoricalData(country,cases,"cases");
            saveHistoricalData(country,deaths,"deaths");

            countryCovidInfo.setLast7daysCases(calculateTotal7DaysCases(cases));
            countryCovidInfo.setLast7daysDeaths(calculateTotal7DaysCases(deaths));

            if (!(country.equals("Burundi") || country.equals("Marshall Islands") ||
                    country.equals("Palau") || country.equals("Vanuatu") || country.equals("New Caledonia"))) {

                if (country.equals("UK")) {
                    country = "U.K.";
                } else if (country.equals("Congo")) {
                    country = "Republic of the Congo";
                } else if (country.equals("DRC")) {
                    country = "DR Congo";
                } else if (country.equals("USA")) {
                    country = "U.S.";
                } else if (country.equals("China")) {
                    country = "Mainland China";
                } else if (country.equals("Macedonia")) {
                    country = "North Macedonia";
                } else if (country.equals("Libyan Arab Jamahiriya")) {
                    country = "Libya";
                } else if (country.equals("Côte d'Ivoire")) {
                    country = "Ivory Coast";
                } else if (country.equals("Lao People's Democratic Republic")) {
                    country = "Laos";
                } else if (country.equals("Syrian Arab Republic")) {
                    country = "Syria";
                } else if (country.equals("Cabo Verde")) {
                    country = "Cape Verde";
                }


                CSVRecord record = CovidDataUtils.findByCountryName(vaccinationInfo, country);


                long dosesAdm = record.get("dosesAdministered").length() > 0 ? Long.parseLong(record.get("dosesAdministered")) : 0;
                countryCovidInfo.setTotalVaccineDosesAdministrated(dosesAdm);
                long fullyVaccinated = record.get("completedVaccination").length() > 0 ? Long.parseLong(record.get("completedVaccination")) : 0;
                countryCovidInfo.setPeopleFullyVaccinated(fullyVaccinated);
                long peopleVaccinated = record.get("peopleVaccinated").length() > 0 ? Long.parseLong(record.get("peopleVaccinated")) : 0;
                countryCovidInfo.setPeopleVaccinatedAtLeastOneDose(peopleVaccinated);
                countryCovidInfo.setPeoplePartiallyVaccinated(peopleVaccinated - fullyVaccinated);
                long boosterDosesAdm = record.get("boosterDosesAdministered").length() > 0 ? Long.parseLong(record.get("boosterDosesAdministered")) : 0;
                countryCovidInfo.setBoosterDosesAdministrated(boosterDosesAdm);
                long population = countryCovidInfo.getPopulation();
                countryCovidInfo.setPeopleVaccinatedAtLeastOneDose(peopleVaccinated);
                countryCovidInfo.setPercentageVaccinatedAtLeastOneDose(Math.round((peopleVaccinated*100/population) * 10000.0)/10000.0);

            }


            countryCovidInfoRepo.save(countryCovidInfo);

        }
    }


    private List<CSVRecord> getCountriesVaccinationData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VACCINE_DATA))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        List<CSVRecord> records = csvParser.getRecords();

        return records;
    }

    //-------------------------------Historical Info----------------------------//

    private void saveHistoricalData(String country, LinkedHashMap<String,Long> map,String list) {

        LinkedHashMap<String,Long> orderedMap = calculateMonthlyCases(map);

        int position = 0;

        for(String date : orderedMap.keySet()) {
            HistoricalCovidInfo historicalCovidInfo = new HistoricalCovidInfo();
            historicalCovidInfo.setCountry(country);
            historicalCovidInfo.setList(list);
            historicalCovidInfo.setPosition(position);
            historicalCovidInfo.setDate(date);
            historicalCovidInfo.setValue(orderedMap.get(date));
            historicalCovidInfoRepo.save(historicalCovidInfo);
            position++;
        }

    }

    //---------------------------------Order data in maps-----------------------------------//

    //calculate every 30 days cases or deaths for chart
    private LinkedHashMap<String,Long> calculateMonthlyCases(LinkedHashMap<String,Long> map1) {
        LinkedHashMap<String,Long> map = calculateEverydayCases(map1);
        LinkedList<String> dates = getAllDays(map.size());
        LinkedHashMap<String,Long> monthlyCases = new LinkedHashMap<>();

        int num = 0;
        int num1 = map.size();
        long totalMonthly = 0;

        for(String date : dates){
            num1--;
            num++;

            if(map.get(date) == null) {
                totalMonthly += 0;
            } else {
                totalMonthly = map.get(date) + totalMonthly;
            }

            if(num == 30 || num1==0) {
                monthlyCases.put(date,totalMonthly);
                num = 0;
                totalMonthly = 0;
            }

        }

        return monthlyCases;
    }

    //calculate everyday cases or deaths
    //the historical data from api is the total numbers of confirmed cases or deaths
    // until each day
    private LinkedHashMap<String,Long> calculateEverydayCases(LinkedHashMap<String,Long> map) {
        LinkedHashMap<String,Long> orderedMap = new LinkedHashMap<>();
        LinkedList<String> dates = getAllDays(map.size());

            for (int i = 1; i < map.size(); i++) {
                if (map.get(dates.get(i)) == null || map.get(dates.get(i - 1)) == null) {
                    return orderedMap;
                }
                orderedMap.put(dates.get(i), map.get(dates.get(i)) - map.get(dates.get(i - 1)));
            }


        return orderedMap;
    }

    //calculate total number of confirmed cases or deaths for last 7 days
    private Long calculateTotal7DaysCases(LinkedHashMap<String,Long> map) {
        LinkedList<String> dates = getAllDays(9);
        LinkedHashMap<String,Long> map1 = new LinkedHashMap<>();

        for(String date : dates) {
            map1.put(date,map.get(date));
        }

        LinkedHashMap<String,Long> map2 = calculateEverydayCases(map1);

        long totalCases = 0;
        for(String date : map2.keySet()) {
            totalCases = totalCases + map2.get(date);
        }

        return totalCases;
    }

    //create a list of date for ordered data in maps
    private LinkedList<String> getAllDays(int time) {
        LinkedList<String> datesList =  new LinkedList<>();
        DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yy");
        DateFormat dateFormat2 = new SimpleDateFormat("MM/d/yy");
        DateFormat dateFormat3 = new SimpleDateFormat("M/dd/yy");
        DateFormat dateFormat4 = new SimpleDateFormat("M/d/yy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -time);
        for (int i = 0; i < time; i++) {
            cal.add(Calendar.DATE, +1);
                String day = dateFormat1.format(cal.getTime());
                if (day.charAt(0) == '0' && day.charAt(3) == '0') {
                    day = dateFormat4.format(cal.getTime());
                } else if (day.charAt(0) == '0') {
                    day = dateFormat3.format(cal.getTime());
                } else if (day.charAt(3) == '0') {

                    day = dateFormat2.format(cal.getTime());
                }
                datesList.add(day);
        }
        return datesList;
    }

    //------------------------------------------------------------------------------------------//

    //delete countries about there is no complete information
    //or change countries name with official name from list with objects with general covid data
    private List<CountryData> removeCountriesData(List<CountryData> list) {

        List<String> countriesForRemoving = countriesListForRemoving();

        for(String country : countriesForRemoving) {
            if(country.equals("Czechia")) {
                CountryData countryData = CovidDataUtils.findByName(list,country);
                countryData.setCountry("Czech Republic");
            } else if (country.equals("Bosnia")) {
                CountryData countryData = CovidDataUtils.findByName(list,country);
                countryData.setCountry("Bosnia and Herzegovina");
            } else if (country.equals("S. Korea")) {
                CountryData countryData = CovidDataUtils.findByName(list,country);
                countryData.setCountry("South Korea");
            } else if (country.equals("Hong Kong")|| country.equals("New Caledonia") || country.equals("Anguilla")
                    || country.equals("Aruba") || country.equals("Bermuda") || country.equals("Cayman Islands")
                    || country.equals("Greenland") || country.equals("Montserrat")) {
                CountryData countryData = CovidDataUtils.findByName(list, country);
                countryData.setCountry(country);
            } else {
                CountryData countryData = CovidDataUtils.findByName(list, country);
                list.remove(countryData);
            }
        }

        return list;
    }

    //delete countries about which there is no complete information
    //or change countries name with official name from list with objects with historical covid data
    private List<CountryHistoricalData> removeCountriesHistoricalData(List<CountryHistoricalData> list) {

        List<String> countriesForRemoving = countriesListForRemoving();

        for(String country : countriesForRemoving) {
            if(country.equals("Czechia")) {
                CountryHistoricalData countryData = CovidDataUtils.findByCountry(list,country);
                countryData.setCountry("Czech Republic");
            } else if (country.equals("Bosnia")) {
                CountryHistoricalData countryData = CovidDataUtils.findByCountry(list,country);
                countryData.setCountry("Bosnia and Herzegovina");
            } else if (country.equals("S. Korea")) {
                CountryHistoricalData countryData = CovidDataUtils.findByCountry(list,country);
                countryData.setCountry("South Korea");
            } else if (country.equals("Hong Kong")|| country.equals("New Caledonia") || country.equals("Anguilla")
                    || country.equals("Aruba") || country.equals("Bermuda") || country.equals("Cayman Islands")
                    || country.equals("Greenland") || country.equals("Montserrat")) {
                CountryHistoricalData countryData = CovidDataUtils.findByProvince(list, country.toLowerCase());
                countryData.setCountry(country);

            } else {
                CountryHistoricalData countryData = CovidDataUtils.findByCountry(list, country);
                list.remove(countryData);
            }
        }

        return list;
    }


    //return the list of countries to be removed
    private List<String> countriesListForRemoving() {
        List<String> countriesForRemoving = new LinkedList<>();
        countriesForRemoving.add("Channel Islands");
        countriesForRemoving.add("Holy See (Vatican City State)");
        countriesForRemoving.add("Faroe Islands");
        countriesForRemoving.add("Isle of Man");
        countriesForRemoving.add("Gibraltar");
        countriesForRemoving.add("Burundi");
        countriesForRemoving.add("Eritrea");
        countriesForRemoving.add("Mayotte");
        countriesForRemoving.add("Réunion");
        countriesForRemoving.add("Western Sahara");
        countriesForRemoving.add("Swaziland");
        countriesForRemoving.add("Saint Helena");
        countriesForRemoving.add("Macao");
        countriesForRemoving.add("Myanmar");
        countriesForRemoving.add("Palestine");
        countriesForRemoving.add("French Polynesia");
        countriesForRemoving.add("Wallis and Futuna");
        countriesForRemoving.add("British Virgin Islands");
        countriesForRemoving.add("Guadeloupe");
        countriesForRemoving.add("Martinique");
        countriesForRemoving.add("Saint Martin");
        countriesForRemoving.add("Saint Pierre Miquelon");
        countriesForRemoving.add("Sint Maarten");
        countriesForRemoving.add("Turks and Caicos Islands");
        countriesForRemoving.add("Caribbean Netherlands");
        countriesForRemoving.add("Saint Lucia");
        countriesForRemoving.add("St. Barth");
        countriesForRemoving.add("Saint Vincent and the Grenadines");
        countriesForRemoving.add("Falkland Islands (Malvinas)");
        countriesForRemoving.add("French Guiana");
        countriesForRemoving.add("Czechia");
        countriesForRemoving.add("Bosnia");
        countriesForRemoving.add("S. Korea");
        countriesForRemoving.add("Hong Kong");
        countriesForRemoving.add("New Caledonia");
        countriesForRemoving.add("Anguilla");
        countriesForRemoving.add("Aruba");
        countriesForRemoving.add("Bermuda");
        countriesForRemoving.add("Cayman Islands");
        countriesForRemoving.add("Curaçao");
        countriesForRemoving.add("Greenland");
        countriesForRemoving.add("Montserrat");
        countriesForRemoving.add("MS Zaandam");
        countriesForRemoving.add("Diamond Princess");

        return countriesForRemoving;
    }
}
