package com.CatyHW.controller;

import com.CatyHW.model.request.SettingCurrencyReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;
import java.nio.charset.Charset;


@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {
    @Autowired
    private  CurrencyController currencyController;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws  Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    @Transactional
    @Rollback
    public void addCurrency() throws Exception {
        SettingCurrencyReq param = new SettingCurrencyReq();
        param.setCurrencyCode("TWD");
        param.setChineseName("新臺幣");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/currency")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);

    }

    @Test
    @Transactional
    @Rollback
    public void updCurrency() throws Exception {
        SettingCurrencyReq param = new SettingCurrencyReq();
        param.setCurrencyCode("TWD");
        param.setChineseName("新臺幣");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/currency/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);
        String body = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(body);
    }

    @Test
    @Transactional
    @Rollback
    public void delCurrency() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/currency/3")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);

    }
    @Test
    public void getCurrentPrice() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/currentPrice").characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);
        String body = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(body);
    }

    @Test
    public void getAllCurrency() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/currencies").characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);
        String body = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(body);
    }


    @Test
    public void getCurrentRate() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getCurrentRate").characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int statusCode = result.getResponse().getStatus();
        Assert.assertEquals(statusCode,200);
        String body = result.getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(body);

    }

}
