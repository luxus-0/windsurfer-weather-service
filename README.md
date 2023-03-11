                                  Aplikacja serwisu pogodowego dla windsurferów
                                  

Ta aplikacja wykorzystuje API Weatherbit Forecast do uzyskania prognoz pogody na 16 dni w pięciu wybranych lokalizacjach dla windsurferów na całym świecie. Interfejs API REST jest udostępniany dla użytkowników, którzy chcą uzyskać najlepszą lokalizację windsurfingową na podstawie temperatury i prędkości wiatru dla danego dnia.
Jak działa aplikacja?
Aplikacja ma interfejs API REST, który odbiera argumentem dzień w formacie rrrr-mm-dd i zwraca odpowiedź z najlepszą lokalizacją windsurfingową na podstawie prognozy pogody na ten dzień.<br><br><br>


                                             Endpoint API
                                              

http://localhost:8080/api/v1/surfing-places/{date}

Endpoint zwraca najlepszą lokalizację windsurfingową dla danego dnia licząc od podanej daty przez 16 dni.Dzień musi być podany w formacie rrrr-mm-dd.
Jeśli będzie zły format to pojawi się komunikat:<br><br>


![image](https://user-images.githubusercontent.com/74199705/224489328-3e63a1a4-a76e-46a9-a0ba-cdfb23005b49.png)



                                            PRZYKŁAD ENDPOINT API
                                            
http://localhost:8080/api/v1/surfing-places/2023-03-11<br><br><br>

                                                    JSON
                                                    

![image](https://user-images.githubusercontent.com/74199705/224482127-a474f8d5-490d-4ae3-acfe-06c94d5b0bec.png)<br><br><br>



                                                Lista lokalizacji


Aplikacja ma pięć wybranych lokalizacji windsurfingowych:

<b>1.Jastarnia (Polska)</b><br>
<b>2.Bridgetown (Barbarados)</b><br>
<b>3.Fortaleza (Brazylia)</b><br>
<b>4.Pissouri (Cypr)</b><br>
<b>5.Le Monre (Mauritius)</b><br>

Lokalizacje mogą być edytowane lub rozszerzane bez konieczności korzystania z interfejsu API. Dane o lokalizacjach są przechowywane w pliku CSV w formacie:
date, city, max_temp, min_temp, wind_spd.<br><br><br>



                                            Kryteria wyboru lokalizacji
                                            
Aplikacja określa najlepszą lokalizację windsurfingową na podstawie dwóch kryteriów: prędkości wiatru i temperatury dla danego dnia.
Jeśli prędkość wiatru nie mieści się w przedziale <5;18>(m/s), a temperatura nie mieści się w przedziale <5;35>(st.C), lokalizacja nie nadaje się do uprawiania windsurfingu.
Jeśli prędkość wiatru i temperatura mieszczą się w powyższych przedziałach, aplikacja oblicza wartość zgodnie z następującym wzorem: v * 3 + temp
gdzie v to prędkość wiatru w m/s dla danego dnia, a temp to średnia prognozowana temperatura na dany dzień w stopniach Celsjusza. Aplikacja zwraca nazwę lokalizacji z najwyższą wartością.<br><br><br>


                                                  Technologie
                                                  

Aplikacja jest napisana w języku Java i wykorzystuje framework Spring Boot.<br><br><br>



                                                    Budowanie i Uruchamianie aplikacji
                                    

Aby uruchomić aplikację, należy postępować zgodnie z poniższymi krokami:


<b>1.Pobierz i zainstaluj Java 8 lub wyższą wersję oraz Gradle lub Maven.</b>

<b>2.Pobierz repozytorium z GitHub:<br>
git clone https://github.com/luxus-0/windsurfer-weather-service.git</b>


<b>3.Otwórz terminal w katalogu głównym aplikacji i wykonaj polecenie:<br>
./gradlew bootRun
lub
mvn spring-boot:run</b>

<b>Aplikacja powinna teraz uruchomić się lokalnie na porcie 8080.</b><br>
<b>4.Otwórz przeglądarkę i przejdź do adresu http://localhost:8080/swagger-ui.html, aby wyświetlić dokumentację interfejsu API REST.<br><br><br>
                                                           
                                                        

                                                       Rozszerzanie listy lokalizacji
                                                    

Lista lokalizacji windsurfingowych jest zdefiniowana w pliku src/main/resources/BestWeatherForWindsurfing.json. Możesz edytować ten plik, aby dodać lub usunąć poszczególne parametry.
Oczywiście można też postawić bazę danych np: PostgreSQL lub MongoDb i postawić dockera.<br><br><br>


                                                  
                                                    Format pliku BestWeatherForWindsurfing.json


  ![image](https://user-images.githubusercontent.com/74199705/224487070-932395c9-b52c-4f7b-9f02-f8ac9ee68036.png)
<br><br>
                                                                          Autor: Łukasz Nowogórski<br>
                                                                          Facebook: https://www.facebook.com/profile.php?id=100000975680046<br>
                                                                          emailL: nowogorski.lukasz0@gmail.com<br>
