package com.springboot.journalApp.service;

import com.springboot.journalApp.apiResponse.WeatherResponse;
import com.springboot.journalApp.cache.AppCache;
import com.springboot.journalApp.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Component
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private  String apiKey ;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String url = appCache.APP_CACHE.get("weather_api").replace("<apiKey>", apiKey).replace("<city>", city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

    public WeatherResponse updateWeather(String city) {
        String url = appCache.APP_CACHE.get("weather_api").replace("<apiKey>", apiKey).replace("<city>", city);

//        String requestBody = "{\n" +
//                "    \"username\": \"Divam\",\n" +
//                "    \"password\": \"Password\"\n" +
//                "}";

        Users users = Users.builder().username("Prachi").password("Password").build();
        HttpEntity<Users> httpEntity = new HttpEntity<>(users);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }
}
