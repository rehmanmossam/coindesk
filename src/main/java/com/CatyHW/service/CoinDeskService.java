package com.CatyHW.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "coindesk-service",url = "https://api.coindesk.com")
public interface CoinDeskService {
    @GetMapping("/v1/bpi/currentprice.json")
    String getCurrentPrice();
}
