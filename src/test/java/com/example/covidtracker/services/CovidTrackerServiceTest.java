package com.example.covidtracker.services;

import com.example.covidtracker.models.CountryCovidInfo;
import com.example.covidtracker.repositories.CountryCovidInfoRepo;
import com.example.covidtracker.repositories.GlobalCovidInfoRepo;
import com.example.covidtracker.repositories.HistoricalCovidInfoRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CovidTrackerServiceTest {

    @MockBean
    GlobalCovidInfoRepo globalCovidInfoRepo;
    @MockBean
    CountryCovidInfoRepo countryCovidInfoRepo;
    @MockBean
    HistoricalCovidInfoRepo historicalCovidInfoRepo;

    @Autowired
    @InjectMocks
    private CovidTrackerService subject;


    @Test
    public void getCountriesNameListTestAreSorted () {

        CountryCovidInfo ro = new CountryCovidInfo();
        ro.setName("Romania");
        CountryCovidInfo fr = new CountryCovidInfo();
        fr.setName("France");
        CountryCovidInfo uk = new CountryCovidInfo();
        uk.setName("UK");

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(ro);
        list.add(uk);
        list.add(fr);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);

        List<String> countries = subject.getCountriesNameList();

        Assert.assertEquals(countries.get(0), fr.getName());
        Assert.assertEquals(countries.get(1), ro.getName());
        Assert.assertEquals(countries.get(2), uk.getName());
    }

    //----------------------------------Tables------------------------------------------//

    @Test
    public void getCountriesDataListOrderedByTotalCases() {
        CountryCovidInfo country1 = new CountryCovidInfo();
        country1.setTotalCases(new Long(10));
        CountryCovidInfo country2 = new CountryCovidInfo();
        country2.setTotalCases(new Long(15));
        CountryCovidInfo country3 = new CountryCovidInfo();
        country3.setTotalCases(new Long(5));
        CountryCovidInfo country4 = new CountryCovidInfo();
        country4.setTotalCases(new Long(0));
        CountryCovidInfo country5 = new CountryCovidInfo();
        country5.setTotalCases(new Long(20));

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.add(country4);
        list.add(country5);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);
        LinkedList<CountryCovidInfo> countries = subject.getCountriesDataList();

        Assert.assertEquals(countries.get(0), country5);
        Assert.assertEquals(countries.get(1), country2);
        Assert.assertEquals(countries.get(2), country1);
        Assert.assertEquals(countries.get(3), country3);
        Assert.assertEquals(countries.get(4), country4);

    }

    @Test
    public void getOrderCountriesBy7daysCases() {
        CountryCovidInfo country1 = new CountryCovidInfo();
        country1.setLast7daysCases(new Long(10));

        CountryCovidInfo country2 = new CountryCovidInfo();
        country2.setLast7daysCases(new Long(15));

        CountryCovidInfo country3 = new CountryCovidInfo();
        country3.setLast7daysCases(new Long(5));

        CountryCovidInfo country4 = new CountryCovidInfo();
        country4.setLast7daysCases(new Long(0));

        CountryCovidInfo country5 = new CountryCovidInfo();
        country5.setLast7daysCases(new Long(20));

        CountryCovidInfo country6 = new CountryCovidInfo();
        country6.setLast7daysCases(new Long(25));

        CountryCovidInfo country7 = new CountryCovidInfo();
        country7.setLast7daysCases(new Long(200));

        CountryCovidInfo country8 = new CountryCovidInfo();
        country8.setLast7daysCases(new Long(4));

        CountryCovidInfo country9 = new CountryCovidInfo();
        country9.setLast7daysCases(new Long(2));

        CountryCovidInfo country10 = new CountryCovidInfo();
        country10.setLast7daysCases(new Long(1));

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.add(country4);
        list.add(country5);
        list.add(country6);
        list.add(country7);
        list.add(country8);
        list.add(country9);
        list.add(country10);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);
        LinkedList<CountryCovidInfo> countries = subject.getOrderCountriesBy7daysCases();

        Assert.assertEquals(countries.get(0), country7);//200
        Assert.assertEquals(countries.get(1), country6);//25
        Assert.assertEquals(countries.get(2), country5);//20
        Assert.assertEquals(countries.get(3), country2);//15
        Assert.assertEquals(countries.get(4), country1);//10
        Assert.assertEquals(countries.get(5), country3);//5
        Assert.assertEquals(countries.get(6), country8);//4
        Assert.assertEquals(countries.get(7), country9);//2
        Assert.assertEquals(countries.get(8), country10);//1
        Assert.assertEquals(countries.get(9), country4);//0
    }

    @Test
    public void getOrderCountriesBy7daysDeaths() {
        CountryCovidInfo country1 = new CountryCovidInfo();
        country1.setLast7daysDeaths(new Long(10));

        CountryCovidInfo country2 = new CountryCovidInfo();
        country2.setLast7daysDeaths(new Long(15));

        CountryCovidInfo country3 = new CountryCovidInfo();
        country3.setLast7daysDeaths(new Long(5));

        CountryCovidInfo country4 = new CountryCovidInfo();
        country4.setLast7daysDeaths(new Long(0));

        CountryCovidInfo country5 = new CountryCovidInfo();
        country5.setLast7daysDeaths(new Long(20));

        CountryCovidInfo country6 = new CountryCovidInfo();
        country6.setLast7daysDeaths(new Long(25));

        CountryCovidInfo country7 = new CountryCovidInfo();
        country7.setLast7daysDeaths(new Long(200));

        CountryCovidInfo country8 = new CountryCovidInfo();
        country8.setLast7daysDeaths(new Long(4));

        CountryCovidInfo country9 = new CountryCovidInfo();
        country9.setLast7daysDeaths(new Long(2));

        CountryCovidInfo country10 = new CountryCovidInfo();
        country10.setLast7daysDeaths(new Long(1));

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.add(country4);
        list.add(country5);
        list.add(country6);
        list.add(country7);
        list.add(country8);
        list.add(country9);
        list.add(country10);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);
        LinkedList<CountryCovidInfo> countries = subject.getOrderCountriesBy7daysDeaths();

        Assert.assertEquals(countries.get(0), country7);//200
        Assert.assertEquals(countries.get(1), country6);//25
        Assert.assertEquals(countries.get(2), country5);//20
        Assert.assertEquals(countries.get(3), country2);//15
        Assert.assertEquals(countries.get(4), country1);//10
        Assert.assertEquals(countries.get(5), country3);//5
        Assert.assertEquals(countries.get(6), country8);//4
        Assert.assertEquals(countries.get(7), country9);//2
        Assert.assertEquals(countries.get(8), country10);//1
        Assert.assertEquals(countries.get(9), country4);//0
    }

    @Test
    public void getOrderCountriesByVaccinationData() {
        CountryCovidInfo country1 = new CountryCovidInfo();
        country1.setPeopleVaccinatedAtLeastOneDose(new Long(10));

        CountryCovidInfo country2 = new CountryCovidInfo();
        country2.setPeopleVaccinatedAtLeastOneDose(new Long(15));

        CountryCovidInfo country3 = new CountryCovidInfo();
        country3.setPeopleVaccinatedAtLeastOneDose(new Long(1));

        CountryCovidInfo country4 = new CountryCovidInfo();
        country4.setPeopleVaccinatedAtLeastOneDose(new Long(0));

        CountryCovidInfo country5 = new CountryCovidInfo();
        country5.setPeopleVaccinatedAtLeastOneDose(new Long(20));

        CountryCovidInfo country6 = new CountryCovidInfo();
        country6.setPeopleVaccinatedAtLeastOneDose(new Long(25));

        CountryCovidInfo country7 = new CountryCovidInfo();
        country7.setPeopleVaccinatedAtLeastOneDose(new Long(200));

        CountryCovidInfo country8 = new CountryCovidInfo();
        country8.setPeopleVaccinatedAtLeastOneDose(new Long(4));

        CountryCovidInfo country9 = new CountryCovidInfo();
        country9.setPeopleVaccinatedAtLeastOneDose(new Long(2));

        CountryCovidInfo country10 = new CountryCovidInfo();
        country10.setPeopleVaccinatedAtLeastOneDose(new Long(1));

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.add(country4);
        list.add(country5);
        list.add(country6);
        list.add(country7);
        list.add(country8);
        list.add(country9);
        list.add(country10);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);
        LinkedList<CountryCovidInfo> countries = subject.getOrderCountriesByVaccinationData();

        Assert.assertEquals(countries.get(0), country7);//200
        Assert.assertEquals(countries.get(1), country6);//25
        Assert.assertEquals(countries.get(2), country5);//20
        Assert.assertEquals(countries.get(3), country2);//15
        Assert.assertEquals(countries.get(4), country1);//10
        Assert.assertEquals(countries.get(5), country3);//5
        Assert.assertEquals(countries.get(6), country8);//4
        Assert.assertEquals(countries.get(7), country9);//2
        Assert.assertEquals(countries.get(8), country10);//1
        Assert.assertEquals(countries.get(9), country4);//0
    }


    @Test
    public void getOrderCountriesByVaccinationDataTheObjectWithNullValueShouldBeLast() {
        CountryCovidInfo country1 = new CountryCovidInfo();
        country1.setPeopleVaccinatedAtLeastOneDose(new Long(10));

        CountryCovidInfo country2 = new CountryCovidInfo();
        country2.setPeopleVaccinatedAtLeastOneDose(new Long(15));

        CountryCovidInfo country3 = new CountryCovidInfo();
        country3.setPeopleVaccinatedAtLeastOneDose(null);

        CountryCovidInfo country4 = new CountryCovidInfo();
        country4.setPeopleVaccinatedAtLeastOneDose(new Long(3));

        CountryCovidInfo country5 = new CountryCovidInfo();
        country5.setPeopleVaccinatedAtLeastOneDose(new Long(20));

        CountryCovidInfo country6 = new CountryCovidInfo();
        country6.setPeopleVaccinatedAtLeastOneDose(new Long(25));

        CountryCovidInfo country7 = new CountryCovidInfo();
        country7.setPeopleVaccinatedAtLeastOneDose(new Long(200));

        CountryCovidInfo country8 = new CountryCovidInfo();
        country8.setPeopleVaccinatedAtLeastOneDose(new Long(4));

        CountryCovidInfo country9 = new CountryCovidInfo();
        country9.setPeopleVaccinatedAtLeastOneDose(new Long(2));

        CountryCovidInfo country10 = new CountryCovidInfo();
        country10.setPeopleVaccinatedAtLeastOneDose(new Long(1));

        LinkedList<CountryCovidInfo> list = new LinkedList<>();
        list.add(country1);
        list.add(country2);
        list.add(country3);
        list.add(country4);
        list.add(country5);
        list.add(country6);
        list.add(country7);
        list.add(country8);
        list.add(country9);
        list.add(country10);

        Mockito.when(countryCovidInfoRepo.findAll()).thenReturn(list);
        LinkedList<CountryCovidInfo> countries = subject.getOrderCountriesByVaccinationData();

        Assert.assertEquals(countries.get(0), country7);//200
        Assert.assertEquals(countries.get(1), country6);//25
        Assert.assertEquals(countries.get(2), country5);//20
        Assert.assertEquals(countries.get(3), country2);//15
        Assert.assertEquals(countries.get(4), country1);//10
        Assert.assertEquals(countries.get(5), country8);//4
        Assert.assertEquals(countries.get(6), country4);//3
        Assert.assertEquals(countries.get(7), country9);//2
        Assert.assertEquals(countries.get(8), country10);//1
        Assert.assertEquals(countries.get(9), country3);//0
    }

}