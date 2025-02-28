package com.walletapp.service;


import com.walletapp.model.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {

    private final RestTemplate restTemplate;


    @Autowired
    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String CONVERT_CURRENCY_URL = "http://localhost:8090/convert";


    public Money convertCurrency(Money money, String toCurrency){
        if(money.getCurrency().equals(toCurrency)){
            return money;
        }

        Map<String, Object> requestBody = Map.of(
                "money", Map.of(
                        "amount", money.getAmount(),
                        "currency", money.getCurrency()
                ),
                "to_currency", toCurrency
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<Map> response = restTemplate.exchange(CONVERT_CURRENCY_URL, HttpMethod.POST, requestEntity, Map.class);

        // Return response body
        Map <String, Double> responseBody = response.getBody();


//        com.currency.proto.Money convertedAmount =
        return new Money(responseBody.get("convertedMoney"), toCurrency);
    }
}
