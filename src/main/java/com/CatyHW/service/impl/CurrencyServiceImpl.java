package com.CatyHW.service.impl;

import com.CatyHW.model.entity.Currency;
import com.CatyHW.model.request.SettingCurrencyReq;
import com.CatyHW.model.response.BpiRes;
import com.CatyHW.model.response.CurrentRateRes;
import com.CatyHW.repository.CurrencyRepository;
import com.CatyHW.service.CoinDeskService;
import com.CatyHW.service.CurrencyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CoinDeskService coinDeskService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    @SneakyThrows
    public Object getCurrentPrice() {
        var jsonStr= coinDeskService.getCurrentPrice();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<String, Object>();
        result = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
        return result;
    }

    @Override
    public ResponseEntity<List<Currency>> getAllCurrency() {
        List<Currency> currencies = new ArrayList<Currency>();
        currencyRepository.findAll().forEach(currencies::add);

        if (currencies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getCurrencyById(Long id) {
        var currency = currencyRepository.findById(id);

        if (!currency.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(currency, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addCurrency(SettingCurrencyReq req) {
        Currency currency = new Currency();
        currency.setCurrencyCode(req.getCurrencyCode());
        currency.setChineseName(req.getChineseName());
        currencyRepository.save(currency);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updCurrency(long id, SettingCurrencyReq req) {
        Currency currency = currencyRepository.findById(id);
        if(currency == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currency.setCurrencyCode(req.getCurrencyCode());
        currency.setChineseName(req.getChineseName());
        currencyRepository.save(currency);
        return new ResponseEntity<Object>(currency,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Object> delCurrency(Long id) {
        Optional<Currency> currency = currencyRepository.findById(id);
        if(!currency.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currencyRepository.deleteById(id);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<CurrentRateRes> getCurrentRate()  {
        CurrentRateRes res = new CurrentRateRes();
        List<BpiRes> bpiList=new ArrayList<>();

        var priceList = getCurrentPrice();

        List<Currency> currencies = new ArrayList<Currency>();
        currencyRepository.findAll().forEach(currencies::add);

        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(priceList, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            val key = entry.getKey();
            val value = entry.getValue();
            if (entry.getKey().equals("time"))
            {
                Map<String, Object> timeMap = oMapper.convertValue(value, Map.class);
                for (Map.Entry<String, Object> entry2 : timeMap.entrySet()) {
                    val entry2Value = entry2.getValue().toString().substring(0,19);
                    if (entry2.getKey().equals("updatedISO")){
                        DateFormat dfUtc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
                        dfUtc.setTimeZone(TimeZone.getTimeZone("UTC"));
                        var time = dfUtc.parse(entry2Value);

                        DateFormat dtc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
                        dtc.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
                        var v =dtc.format(time);
                        res.setUpdatedTime(v);
                    }
                }


            }

            if (entry.getKey().equals("bpi"))
            {
                Map<String, Object> bpiMap = oMapper.convertValue(value, Map.class);
                for (Map.Entry<String, Object> entry2 : bpiMap.entrySet()) {
                    Map<String, Object> coinMap = oMapper.convertValue( entry2.getValue(), Map.class);
                    BpiRes bpi = new BpiRes();
                    for (Map.Entry<String, Object> entry3 : coinMap.entrySet()){
                        if (entry3.getKey().equals("code")){
                            bpi.setCurrencyCode(entry3.getValue().toString());
                        }
                        if (entry3.getKey().equals("rate")){
                            String v =entry3.getValue().toString().replace(",","");
                            BigDecimal bigDecimal = new BigDecimal(v);
                            bpi.setRate(bigDecimal);
                        }
                    }
                    var currency= currencies.stream().filter(u -> u.getCurrencyCode().equals(bpi.getCurrencyCode())).findFirst();
                    if(currency.isPresent()){
                        bpi.setChineseName(currency.get().getChineseName());
                    }
                    bpiList.add(bpi);
                }
            }
            res.setBpi(bpiList);
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
