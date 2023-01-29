package com.CatyHW.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class BpiRes {
    @JsonProperty("currencyCode")
    private String currencyCode;

    @JsonProperty("chineseName")
    private String chineseName;

    @JsonProperty("rate")
    private BigDecimal rate;

}
