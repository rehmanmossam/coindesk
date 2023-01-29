package com.CatyHW.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CurrentRateRes {
    @JsonProperty("updatedTime")
    private String updatedTime;

    @JsonProperty("bpi")
    List<BpiRes> bpi;

}
