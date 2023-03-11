                                  Aplikacja serwisu pogodowego dla windsurferów
                                  

Ta aplikacja wykorzystuje API Weatherbit Forecast do uzyskania prognoz pogody na 16 dni w pięciu wybranych lokalizacjach dla windsurferów na całym świecie. Interfejs API REST jest udostępniany dla użytkowników, którzy chcą uzyskać najlepszą lokalizację windsurfingową na podstawie temperatury i prędkości wiatru dla danego dnia.
Jak działa aplikacja?
Aplikacja ma interfejs API REST, który odbiera argumentem dzień w formacie rrrr-mm-dd i zwraca odpowiedź z najlepszą lokalizacją windsurfingową na podstawie prognozy pogody na ten dzień.


                                             Endpoint API
                                              

http://localhost:8081/api/v1/surfing-places/{date}

Endpoint zwraca najlepszą lokalizację windsurfingową dla danego dnia. Dzień musi być podany w formacie rrrr-mm-dd.
Jeśli będzie zły format to pojawi się komunikat:<br><br>


![image](https://user-images.githubusercontent.com/74199705/224484107-68aa4f6d-1928-448f-b2eb-f439af56f7b7.png)<br>



                                            PRZYKŁAD ENDPOINT API
                                            
http://localhost:8081/api/v1/surfing-places/2023-03-11<br>

                                                    JSON
                                                    

![image](https://user-images.githubusercontent.com/74199705/224482127-a474f8d5-490d-4ae3-acfe-06c94d5b0bec.png)<br>



                                                Lista lokalizacji


Aplikacja ma pięć wybranych lokalizacji windsurfingowych:

<b>1.Jastarnia (Polska)</b><br>
<b>2.Bridgetown (Barbarados)</b><br>
<b>3.Fortaleza (Brazylia)</b><br>
<b>4.Pissouri (Cypr)</b><br>
<b>5.Le Monre (Mauritius)</b><br>

Lokalizacje mogą być edytowane lub rozszerzane bez konieczności korzystania z interfejsu API. Dane o lokalizacjach są przechowywane w pliku CSV w formacie:
date, city, max_temp, min_temp, wind_spd.<br>



                                            Kryteria wyboru lokalizacji
                                            
Aplikacja określa najlepszą lokalizację windsurfingową na podstawie dwóch kryteriów: prędkości wiatru i temperatury dla danego dnia.
Jeśli prędkość wiatru nie mieści się w przedziale <5;18>(m/s), a temperatura nie mieści się w przedziale <5;35>(st.C), lokalizacja nie nadaje się do uprawiania windsurfingu.
Jeśli prędkość wiatru i temperatura mieszczą się w powyższych przedziałach, aplikacja oblicza wartość zgodnie z następującym wzorem: v * 3 + temp
gdzie v to prędkość wiatru w m/s dla danego dnia, a temp to średnia prognozowana temperatura na dany dzień w stopniach Celsjusza. 
Aplikacja zwraca nazwę lokalizacji z najwyższą wartością.<br>


                                                  Technologie
                                                  

Aplikacja jest napisana w języku Java i wykorzystuje framework Spring Boot.<br>



                                    Budowanie i Uruchamianie aplikacji
                                    

Aby uruchomić aplikację, należy postępować zgodnie z poniższymi krokami:


<b>1. Pobierz i zainstaluj Java 8 lub wyższą wersję oraz Gradle lub Maven.</b>


<b>2. Pobierz repozytorium z GitHub:
git clone https://github.com/twoj-login/weather-app.git</b>


<b>3. Otwórz terminal w katalogu głównym aplikacji i wykonaj polecenie:
./gradlew bootRun
lub
mvn spring-boot:run</b>

Aplikacja powinna teraz uruchomić się lokalnie na porcie 8080.


<b>4. Otwórz przeglądarkę i przejdź do adresu http://localhost:8080/swagger-ui.html, aby wyświetlić dokumentację interfejsu API REST.</b><br>
                                                           
                                                        

                                                    Rozszerzanie listy lokalizacji
                                                    

Lista lokalizacji windsurfingowych jest zdefiniowana w pliku src/main/resources/best_weather_for_windsurfing.json. Możesz edytować ten plik, aby dodać lub usunąć poszczególne parametry.
Oczywiście można też postawić bazę danych np: PostgreSQL lub MongoDb i postawić dockera.<br>


                                                  
                                                    Format pliku BestWeatherForWindsurfing.json:


  [
  {
    "date": "2023-03-11",
    "city": "Fortaleza",
    "max_temp": 26.6,
    "min_temp": 24.8,
    "wind_spd": 5.5
  },
  {
    "date": "2023-03-12",
    "city": "Bridgetown",
    "max_temp": 27.2,
    "min_temp": 25.3,
    "wind_spd": 6.5
  },
  {
    "date": "2023-03-13",
    "city": "Jastarnia",
    "max_temp": 27.4,
    "min_temp": 25.4,
    "wind_spd": 5.9
  },
  {
    "date": "2023-03-14",
    "city": "Bridgetown",
    "max_temp": 27.3,
    "min_temp": 24.9,
    "wind_spd": 6.9
  },
  {
    "date": "2023-03-15",
    "city": "Bridgetown",
    "max_temp": 26.9,
    "min_temp": 25.2,
    "wind_spd": 7.7
  },
  {
    "date": "2023-03-16",
    "city": "Bridgetown",
    "max_temp": 26.5,
    "min_temp": 25.1,
    "wind_spd": 7.3
  },
  {
    "date": "2023-03-17",
    "city": "Brak możliwości do surfowania",
    "max_temp": 15.4,
    "min_temp": 9.9,
    "wind_spd": 3.8
  },
  {
    "date": "2023-03-18",
    "city": "Bridgetown",
    "max_temp": 26.9,
    "min_temp": 24.9,
    "wind_spd": 6.3
  },
  {
    "date": "2023-03-19",
    "city": "Bridgetown",
    "max_temp": 26.6,
    "min_temp": 24.8,
    "wind_spd": 7.4
  },
  {
    "date": "2023-03-20",
    "city": "Bridgetown",
    "max_temp": 26.4,
    "min_temp": 24.8,
    "wind_spd": 6.5
  },
  {
    "date": "2023-03-21",
    "city": "Bridgetown",
    "max_temp": 26.4,
    "min_temp": 25.1,
    "wind_spd": 5.7
  },
  {
    "date": "2023-03-22",
    "city": "Bridgetown",
    "max_temp": 26.6,
    "min_temp": 24.9,
    "wind_spd": 6.6
  },
  {
    "date": "2023-03-23",
    "city": "Bridgetown",
    "max_temp": 26.4,
    "min_temp": 24.8,
    "wind_spd": 7.4
  },
  {
    "date": "2023-03-24",
    "city": "Bridgetown",
    "max_temp": 26.7,
    "min_temp": 24.9,
    "wind_spd": 7.3
  },
  {
    "date": "2023-03-25",
    "city": "Bridgetown",
    "max_temp": 26.6,
    "min_temp": 25.3,
    "wind_spd": 7.5
  },
  {
    "date": "2023-03-26",
    "city": "Bridgetown",
    "max_temp": 25.3,
    "min_temp": 25.3,
    "wind_spd": 6.9
  }
]

                                                                          Autor: Łukasz Nowogórski
                                                                          Facebook: https://www.facebook.com/profile.php?id=100000975680046
                                                                          emailL: nowogorski.lukasz0@gmail.com<b>
