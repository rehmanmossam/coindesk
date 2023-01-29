package com.CatyHW.service;

import com.CatyHW.model.entity.Currency;
import com.CatyHW.model.request.SettingCurrencyReq;
import com.CatyHW.model.response.CurrentRateRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CurrencyService {

    Object getCurrentPrice();
    ResponseEntity<List<Currency>> getAllCurrency();

    ResponseEntity<Object> getCurrencyById(Long id);

    ResponseEntity<Object> addCurrency(SettingCurrencyReq req);

    ResponseEntity<Object> updCurrency(long id, SettingCurrencyReq req);

    ResponseEntity<Object> delCurrency(Long id);

    ResponseEntity<CurrentRateRes> getCurrentRate();

}
