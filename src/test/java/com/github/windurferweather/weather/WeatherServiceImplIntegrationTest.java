package com.github.windurferweather.weather;


import com.github.windurferweather.weather.dto.LocationDto;
import com.github.windurferweather.weather.dto.WeatherResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static com.github.windurferweather.weather.WeatherConstant.API_KEY;
import static com.github.windurferweather.weather.WeatherConstant.ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplIntegrationTest {

    private final MockMvc mockMvc;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private WeatherServiceImpl weatherService;

    public WeatherServiceImplIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void shouldReturnLocationForValidInput() throws Exception {

        String date = "2023-03-05";

        MvcResult dateExpected = mockMvc.perform(MockMvcRequestBuilders.get("location_for_windsurfing/" + date)
                        .param("date", date))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = dateExpected.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }
}
