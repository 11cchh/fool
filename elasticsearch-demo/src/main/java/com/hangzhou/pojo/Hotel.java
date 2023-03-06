package com.hangzhou.pojo;

import lombok.Data;

/**
 * @Author Faye
 * @Date 2023/1/3 09:41
 */
@Data
public class Hotel {
    /**
     * _id
     */
    private String id;

    /**
     * 索引名称
     */
    private String index;

    /**
     * 文档得分
     */
    private Float score;

    /**
     * 对应索引中的字段
     */
    private String title;

    /**
     * 对应索引中的字段
     */
    private String city;

    /**
     * 对应索引中的字段
     */
    private Integer price;
}
