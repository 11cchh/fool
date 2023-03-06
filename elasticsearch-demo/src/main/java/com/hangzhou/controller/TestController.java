package com.hangzhou.controller;

import com.alibaba.fastjson.JSONObject;
import com.hangzhou.pojo.Hotel;
import com.hangzhou.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @Author Faye
 * @Date 2023/1/3 09:58
 */
@RestController
public class TestController {
    @Autowired
    private EsService esService;

    @RequestMapping("/hotel")
    public String getHotel(String keyword) {
        List<Hotel> hotels = esService.getHotelByTitle(keyword);
        if (CollectionUtils.isEmpty(hotels)) {
            return "no data";
        }
        return JSONObject.toJSONString(hotels);
    }
}
