$(document).ready(function() {
    let todayCases = $('#casesToday');
    let todayDeaths = $('#deathsToday');
    let todayRecovered = $('#recoveredToday');
    let totalCases = $('#casesTotal');
    let totalDeaths = $('#deathsTotal');
    let totalRecovered = $('#recoveredTotal');
    let dropdown = $('#countries');
    $('#country').text("Global");

    const url1 = "/countries-names";
    const url2 = "/globals";
    const url3 = "/charts-global";
    const url4 = "/countries-all";
    const url5 ="/table-vaccinatedAtLeastOneDose";
    const url7 ="/countries-7daysCases";
    const url8 ="/countries-7daysDeaths";

    let countries;
    let globalData;
    let charts;
    let countriesInfo;
    let table1;
    let table3;
    let table4;

    $.when(
        $.getJSON(url1, function(data) {
            countries = data;
        }),
        $.getJSON(url2, function(data) {
            globalData = data;
        }),
        $.getJSON(url3, function(data) {
            charts = data;
        }),$.getJSON(url4, function(data) {
            countriesInfo = data;
        }),
        $.getJSON(url5, function(data) {
            table1 = data;
        }),
        $.getJSON(url7, function(data) {
            table3 = data;
        }),
        $.getJSON(url8, function(data) {
            table4 = data;
        })).done(function(){
            dropdown.append('<option selected="true" disabled>Choose Country</option>');

            $.each(countries, function (key, entry) {
                dropdown.append($('<option></option>').attr('value', entry).text(entry));
            })

            $.each(globalData, function (key, entry) {
                 todayCases.append('<div class="card-body"><h1 align="center">' + entry.todayCases.toLocaleString() + '</h1>' +
                     '<h4 align="center">Today Confirmed Cases</h4></div>');
                 todayDeaths.append('<div class="card-body"><h1 align="center">' + entry.todayDeaths.toLocaleString() + '</h1>' +
                     '<h4 align="center">Today Confirmed Deaths</h4></div');
                 todayRecovered.append('<div class="card-body"><h1 align="center">' + entry.todayRecovered.toLocaleString() +'</h1>' +
                     '<h4 align="center">Today Confirmed Recovered</h4></div>');
                 totalCases.append('<div class="card-body"><h1 align="center">' + entry.totalCases.toLocaleString() + '</h1>' +
                     '<h4 align="center">Total Confirmed Cases</h4></div>');
                 totalDeaths.append('<div class="card-body"><h1 align="center">' + entry.totalDeaths.toLocaleString() + '</h1>' +
                     '<h4 align="center">Total Confirmed Deaths</h4></div>');
                 totalRecovered.append('<div class="card-body"><h1 align="center">' + entry.totalRecovered.toLocaleString() + "  " + '</h1>' +
                     '<h4 align="center">Total Confirmed Recovered</h4></div>');

                 let values;
                 values = $('<tr>');
                 values.append('<td>' + entry.totalVaccineDosesAdministrated.toLocaleString() + '</td>');
                 values.append('<td>' + entry.peopleFullyVaccinated.toLocaleString() + '</td>');
                 values.append('<td>' + (entry.peopleFullyVaccinated * 100 / entry.population).toFixed(2) + "%" + '</td>');
                 values.append('<td>' + entry.peoplePartiallyVaccinated.toLocaleString() + '</td>');
                 values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                 values.append('<td>' + ((entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated) *100/entry.population).toFixed(2).toLocaleString() + "%" + '</td>');
                 values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                 values.append('<td>' + entry.population.toLocaleString() + '</td>');
                 $('#vaccineTable').append(values);
            })

            createChart1(charts.keys,charts.deaths,charts.cases);
            createChart2(charts.activeCases,charts.recovered,charts.deathsTotal);
            createChart3(charts.population,charts.activeCases);
            createChart4(charts.population,charts.vaccinatedAtLeastOneDoses);
            createChart5(charts.boosterDoses,charts.partiallyVaccinated,
                charts.fullyVaccinated);

            $.each(table1, function (key, entry) {
                let values;
                values = $('<tr>');
                values.append('<td>' + (key+1) + '</td>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.peopleVaccinatedAtLeastOneDose.toLocaleString()  + '</td>');
                $('#topTotalVaccinated').append(values);
            });

            $.each(table3, function (key, entry) {
                let values;
                values = $('<tr>');
                values.append('<td>' + (key+1) + '</td>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.last7daysCases.toLocaleString() + '</td>');
                $('#topLast7daysCases').append(values);
            });

            $.each(table4, function (key, entry) {
               let values;
               values = $('<tr>');
               values.append('<td>' + (key+1) + '</td>');
               values.append('<td>' + entry.name + '</td>');
               values.append('<td>' + entry.last7daysDeaths.toLocaleString() + '</td>');
               $('#topLast7daysDeaths').append(values);
            });

            $.each(countriesInfo, function (key, entry) {
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })
        });
})

function createChart1(keys,deaths,cases) {

    $('#casesChart').remove();
    $('#title').remove();
    $('#mainChart').append('<div id="title"><h3 align="center">Confirmed Cases and Deaths for every 30 days</h3></div>')
    $('#mainChart').append('<canvas id="casesChart"></canvas>');
    const ctx = document.getElementById('casesChart').getContext('2d');
    let chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: keys,
            datasets: [
                {
                    label: 'Cases',
                    data: cases,
                    backgroundColor: 'transparent',
                    borderColor: '#ff4646',
                    borderWidth: 4
                },
                {
                    label: 'Deaths',
                    data: deaths,
                    backgroundColor: 'transparent',
                    borderColor: 'black',
                    borderWidth: 4
                }]
        },
        options: {
            responsive: true,
            elements: {
                line: {
                    tension: 0
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

function createChart2(activeCases,recovered,deaths) {
    $('#chartTotalCases').remove();
    $('#chart1').append('<div id="chartTotalCases"></div>');
    $('#chartTotalCases').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Total Confirmed Cases'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                colors: [
                    '#ff4646',
                    '#68a55a',
                    '#6c6c6c'
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'People:',
            colorByPoint: true,
            data: [{
                name: 'Active Cases',
                y: activeCases,
                sliced: true,
                selected: true
            }, {
                name: 'Confirmed Recovered',
                y: recovered
            },{
                name: 'Confirmed Deaths',
                y: deaths
            }]
        }]
    })
}

function createChart3(population,activeCases) {

    let second = population-activeCases
    $('#chartNonInfected').remove();
    $('#chart2').append('<div id="chartNonInfected"></div>');

    $('#chartNonInfected').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Active Cases'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                colors: [
                    '#ff4646',
                    '#5a8aa5'
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'People:',
            colorByPoint: true,
            data: [{
                name: 'Active Cases',
                y: activeCases,
                sliced: true,
                selected: true
            }, {
                name: 'Uninfected',
                y: second
            }]
        }]
    })
}
function createChart4(population,vaccinatedAtLeastOneDoses) {
    let first = vaccinatedAtLeastOneDoses;
    let second = population-vaccinatedAtLeastOneDoses;
    $('#chartNonVaccinated').remove();
    $('#chart3').append('<div id="chartNonVaccinated"></div>');
    $('#chartNonVaccinated').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Total Vaccinated People'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                colors: [
                    '#5a8aa5',
                    '#ff4646'
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'People:',
            colorByPoint: true,
            data: [{
                name: 'Vaccinated At Least One Doses',
                y: first,
                sliced: true,
                selected: true
            }, {
                name: 'Unvaccinated',
                y: second
            }]
        }]
    })
}

function createChart5(boosterDoses,partiallyVaccinated,fullyVaccinated) {
    $('#chartVaccination').remove();
    $('#chart4').append('<div id="chartVaccination"></div>');

    $('#chartVaccination').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Total Vaccine Doses Administrated'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                colors: [
                    '#eddd88',
                    '#5aa572',
                    '#5a8aa5'
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'People:',
            colorByPoint: true,
            data: [{
                name: '1st Dose Vaccine Administrated',
                y: partiallyVaccinated,
                sliced: true,
                selected: true
            }, {
                name: '2st Dose Vaccine Administrated',
                y: fullyVaccinated
            }, {
                name: 'Booster Doses Administrated',
                y: boosterDoses
            }]
        }]
    })
}

$("#europe").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'Europe';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                console.log(entry.name);
                console.log(entry.continent);
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});

$("#australia").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'Australia-Oceania';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                console.log(entry.name);
                console.log(entry.continent);
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});
$("#africa").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'Africa';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});
$("#asia").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'Asia';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                console.log(entry.name);
                console.log(entry.continent);
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});

$("#northAmerica").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'North America';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                console.log(entry.name);
                console.log(entry.continent);
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});
$("#southAmerica").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let continent = 'South America';
    $.ajax({
        type: "POST",
        url: "/countries-all-by-continent",
        data: JSON.stringify({
            "continent": continent,
        }),
        contentType: "application/json;",
        success: function(data) {
            $.each(data, function (key, entry) {
                let values;
                values = $('<tr>');
                values.append('<td>' + entry.name + '</td>');
                values.append('<td>' + entry.continent + '</td>');
                values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
                values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
                values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
                values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
                values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
                values.append('<td>' + entry.active.toLocaleString() + '</td>');
                values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
                values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
                values.append('<td>' + entry.population.toLocaleString() + '</td>');
                $('#countriesTable').append(values);
            })

        },
        error: function() {
            alert('There was a problem!')
        }
    });

});

$("#all").click(function(e) {
    e.preventDefault();
    $('#countriesTable').find("tr:gt(0)").remove();
    let url3 = "/countries-all";
    $.getJSON(url3, function(data) {
        $.each(data, function (key, entry) {
            let values;
            values = $('<tr>');
            values.append('<td>' + entry.name + '</td>');
            values.append('<td>' + entry.continent + '</td>');
            values.append('<td>' + entry.totalCases.toLocaleString() + '</td>');
            values.append('<td bgcolor="#ff4646"> +' + entry.todayCases.toLocaleString() + '</td>');
            values.append('<td>' + entry.totalDeaths.toLocaleString() + '</td>');
            values.append('<td bgcolor="#6c6c6c"> +' + entry.todayDeaths.toLocaleString() + '</td>');
            values.append('<td>' + entry.totalRecovered.toLocaleString() + '</td>');
            values.append('<td bgcolor="#68a55a"> +' + entry.todayRecovered.toLocaleString() + '</td>');
            values.append('<td>' + entry.active.toLocaleString() + '</td>');
            values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
            values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
            values.append('<td>' + entry.population.toLocaleString() + '</td>');
            $('#countriesTable').append(values);
        })
    })

});

$("#chooseCountry").click(function(e) {
    e.preventDefault();
    let country = $('#countries').children("option").filter(":selected").text();
    if(country === "Global") {
        updateGlobalData();
    } else {
        updateCharts(country);
        updateGeneralData(country);
    }

})

async function updateGlobalData() {
    let globalData;
    let charts;
    const url1 = "/globals";
    const url2 = "/charts-global";
    $.when(
        $.getJSON(url1, function(data) {
            globalData = data;
        }),
        $.getJSON(url2, function(data) {
            charts= data;
        })
    ).done(function (){
        $('#country').text("Global")
        $('#casesToday').empty();
        $('#deathsToday').empty();
        $('#recoveredToday').empty();
        $('#casesTotal').empty();
        $('#deathsTotal').empty();
        $('#recoveredTotal').empty();
        $.each(globalData, function (key, entry) {
            $('#casesToday').append('<div class="card-body"><h3 align="center">' + entry.todayCases.toLocaleString() + '</h3>' +
                '<h5 align="center">Today Confirmed Cases</h5></div>');
            $('#deathsToday').append('<div class="card-body"><h3 align="center">' + entry.todayDeaths.toLocaleString() + '</h3>' +
                '<h5 align="center">Today Confirmed Deaths</h5></div');
            $('#recoveredToday').append('<div class="card-body"><h3 align="center">' + entry.todayRecovered.toLocaleString() +'</h3>' +
                '<h5 align="center">Today Confirmed Recovered</h5></div>');
            $('#casesTotal').append('<div class="card-body"><h3 align="center">' + entry.totalCases.toLocaleString() + '</h3>' +
                '<h5 align="center">Total Confirmed Cases</h5></div>');
            $('#deathsTotal').append('<div class="card-body"><h3 align="center">' + entry.totalDeaths.toLocaleString() + '</h3>' +
                '<h5 align="center">Total Confirmed Deaths</h5></div>');
            $('#recoveredTotal').append('<div class="card-body"><h3 align="center">' + entry.totalRecovered.toLocaleString() + "  " + '</h3>' +
                '<h5 align="center">Total Confirmed Recovered</h5></div>');

            $('#vaccineTable').find("tr:gt(0)").remove();

            let values;
            values = $('<tr>');
            values.append('<td>' + entry.totalVaccineDosesAdministrated.toLocaleString() + '</td>');
            values.append('<td>' + entry.peopleFullyVaccinated.toLocaleString() + '</td>');
            values.append('<td>' + (entry.peopleFullyVaccinated * 100 / entry.population).toFixed(2) + "%" + '</td>');
            values.append('<td>' + entry.peoplePartiallyVaccinated.toLocaleString() + '</td>');
            values.append('<td>' + (entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated).toLocaleString() + '</td>');
            values.append('<td>' + ((entry.peopleFullyVaccinated + entry.peoplePartiallyVaccinated) *100/entry.population).toFixed(2).toLocaleString() + "%" + '</td>');
            values.append('<td>' + entry.boosterDosesAdministrated.toLocaleString() + '</td>');
            values.append('<td>' + entry.population.toLocaleString() + '</td>');
            $('#vaccineTable').append(values);
        })

        createChart1(charts.keys,charts.deaths,charts.cases);
        createChart2(charts.activeCases,charts.recovered,charts.deathsTotal);
        createChart3(charts.population,charts.activeCases);
        createChart4(charts.population,charts.vaccinatedAtLeastOneDoses);
        createChart5(charts.boosterDoses,charts.partiallyVaccinated,
            charts.fullyVaccinated);


    })
}

async function updateGeneralData(country) {
    $.ajax({
        type: "POST",
        url: "/country",
        data: JSON.stringify({
            "country": country
        }),
        contentType: "application/json;",
        success: function (data) {
            $('#country').text(data.name)
            $('#casesToday').empty();
            $('#deathsToday').empty();
            $('#recoveredToday').empty();
            $('#casesTotal').empty();
            $('#deathsTotal').empty();
            $('#recoveredTotal').empty();

            $('#casesToday').append('<div class="card-body"><h3 align="center">' + data.todayCases.toLocaleString() + '</h3>' +
                '<h5 align="center">Today Confirmed Cases</h5></div>');
            $('#deathsToday').append('<div class="card-body"><h3 align="center">' + data.todayDeaths.toLocaleString() + '</h3>' +
                '<h5 align="center">Today Confirmed Deaths</h5></div');
            $('#recoveredToday').append('<div class="card-body"><h3 align="center">' + data.todayRecovered.toLocaleString() + '</h3>' +
                '<h5 align="center">Today Confirmed Recovered</h5></div>');
            $('#casesTotal').append('<div class="card-body"><h3 align="center">' + data.totalCases.toLocaleString() + '</h3>' +
                '<h5 align="center">Total Confirmed Cases</h5></div>');
            $('#deathsTotal').append('<div class="card-body"><h3 align="center">' + data.totalDeaths.toLocaleString() + '</h3>' +
                '<h5 align="center">Total Confirmed Deaths</h5></div>');
            $('#recoveredTotal').append('<div class="card-body"><h3 align="center">' + data.totalRecovered.toLocaleString() + "  " + '</h3>' +
                '<h5 align="center">Total Confirmed Recovered</h5></div>');

            $('#vaccineTable').find("tr:gt(0)").remove();
            let values;
            values = $('<tr>');
            values.append('<td>' + data.totalVaccineDosesAdministrated.toLocaleString() + '</td>');
            values.append('<td>' + data.peopleFullyVaccinated.toLocaleString() + '</td>');
            values.append('<td>' + (data.peopleFullyVaccinated * 100 / data.population).toFixed(2) + "%" + '</td>');
            values.append('<td>' + data.peoplePartiallyVaccinated.toLocaleString() + '</td>');
            values.append('<td>' + (data.peopleFullyVaccinated + data.peoplePartiallyVaccinated).toLocaleString() + '</td>');
            values.append('<td>' + ((data.peopleFullyVaccinated + data.peoplePartiallyVaccinated) *100/data.population).toFixed(2).toLocaleString() + "%" + '</td>');
            values.append('<td>' + data.boosterDosesAdministrated.toLocaleString() + '</td>');
            values.append('<td>' + data.population.toLocaleString() + '</td>');
            $('#vaccineTable').append(values);
        },
        error: function () {
            alert('There was a problem!')
        }

    })
}

async function updateCharts(country){
    $.ajax({
        type: "POST",
        url: "/country-charts",
        data: JSON.stringify({
            "country": country
        }),
        contentType: "application/json;",
        success: function (data) {
            createChart1(data.keys,data.deaths,data.cases);
            createChart2(data.activeCases,data.recovered,data.deathsTotal);
            createChart3(data.population,data.activeCases);
            createChart4(data.population,data.vaccinatedAtLeastOneDoses);
            createChart5(data.boosterDoses,data.partiallyVaccinated,
                data.fullyVaccinated);

        },
        error: function () {
            alert('There was a problem!')
        }

    })
}

