package com.hangzhou.pojo;

import lombok.Data;

/**
 * 操作日志
 *
 * @Author Faye
 * @Date 2022/11/29 17:21
 */
@Data
public class OperationLogVO {
    /**
     * 操作描述
     */
    private String desc;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 假定需要记录订单Id，而订单Id在各个方法中的各个类中字段不同
     */
    private Long orderId;
}
