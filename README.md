# Covid-19-Dashboard
the dashboard provides data regarding the covid-19  pandemic situation and anti-pandemic measures by country 

Data source:

- https://raw.githubusercontent.com/BloombergGraphics/covid-vaccine-tracker-data/master/data/current-global.csv;
- https://disease.sh/v3/covid-19/all;
- https://disease.sh/v3/covid-19/historical/all?lastdays=all;
- https://disease.sh/v3/covid-19/countries;
- https://disease.sh/v3/covid-19/historical?lastdays=all;

Main features:

- allows the user to check the data regarding the covid-19 pandemic situation by country using a dropdown button
- shows the vaccination information in a table
- shows the number of covid contractions and deaths for every 30 days in a line chart
- shows the percentage of active covid cases from population in a pie chart
- shows the percentage of people who received at least one dose of a Covid-19 vaccine from population in a pie chart
- shows the percentage of recoveries, deaths and active cases from total number of confirmed cases in a pie chart
- shows the percentage of people who received 1st dose of vaccine, people who received 2nd dose of vaccine and people who received a booster dose from total number of vaccine doses administrated in a pie chart
- the tables show: 1.the top ten countries by number of confirmed cases in the last 7 days; 2. the top ten countries by number of confirmed deaths in the last 7 days; 3. the top ten states with leading vaccination rates
- a table displays data on all countries, sorted by total number of confirmed cases
- the displayed countries can be sorted by continent (a set of seven buttons)
- data is updated every 6 hours (from api)


Application is built using the following technologies:

- Backend : Java 11, Spring Boot, Spring Rest, Spring Redis
- Frontend: HTML, CSS, JavaScript, JQuery with the addition of Charts.js, Highcharts js
- Build: Maven
- Testing: Junit 5


https://user-images.githubusercontent.com/84472171/149630659-4728f709-52c1-4b74-b486-e72499b4d565.mp4


