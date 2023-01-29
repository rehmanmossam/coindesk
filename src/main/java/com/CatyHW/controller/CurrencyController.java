package com.CatyHW.controller;

import com.CatyHW.model.entity.Currency;
import com.CatyHW.model.request.SettingCurrencyReq;
import com.CatyHW.model.response.CurrentRateRes;
import com.CatyHW.service.CurrencyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;


    /**
     * 呼叫coindesk API
     *
     * @return
     */
    @GetMapping(value = "/currentPrice")
    public Object  getCurrentPrice() throws JsonProcessingException {
        return  currencyService.getCurrentPrice();
    }

    /**
     * 查詢幣別
     *
     * @return
     */
    @GetMapping(value = "/currencies")
    public ResponseEntity<List<Currency>> getAllCurrency()  {
        return currencyService.getAllCurrency();
    }

    /**
     * 查詢單一幣別
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/currency/{id}")
    public ResponseEntity<Object> getCurrencyById(@PathVariable("id") Long id)  {
        return currencyService.getCurrencyById(id);
    }

    /**
     * 新增幣別
     *
     * @param currency
     * @return
     */
    @PostMapping(value = "/currency")
    public ResponseEntity<Object> addCurrency(@RequestBody SettingCurrencyReq currency)  {
        return currencyService.addCurrency(currency);
    }


    /**
     * 更新幣別
     *
     * @param currency
     * @return
     */
    @PutMapping(value = "/currency/{id}")
    public ResponseEntity<Object> updCurrency(@PathVariable("id") long id,@RequestBody SettingCurrencyReq currency)  {
        return  currencyService.updCurrency(id,currency);
    }

    /**
     * 刪除幣別
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/currency/{id}")
    public ResponseEntity<Object> delCurrency(@PathVariable("id") Long id)  {
        return currencyService.delCurrency(id);
    }

    /**
     *  呼叫 coindesk 的 API，並進行資料轉換，組成新 API
     *
     * @return
     */
    @GetMapping(value = "/getCurrentRate")
    public ResponseEntity<CurrentRateRes> getCurrentRate()  {
        return currencyService.getCurrentRate();
    }
}
