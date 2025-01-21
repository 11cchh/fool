package com.hangzhou.controller;

import com.hangzhou.pojo.SaveOrder;
import com.hangzhou.pojo.UpdateOrder;
import com.hangzhou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Faye
 * @Date 2022/11/29 17:47
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("save")
    public SaveOrder saveOrder() {
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);
        orderService.saveOrder(saveOrder);
        // 主业务发生异常时，切面逻辑无法回滚
        int i = 10/0;
        return saveOrder;
    }

    @GetMapping("update")
    public UpdateOrder updateOrder() {
        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);
        orderService.updateOrder(updateOrder);
        return updateOrder;
    }

    @GetMapping("test")
    public String test() {
        return "json";
    }
}
