package com.hangzhou.service;

import com.hangzhou.aop.OperationLog;
import com.hangzhou.pojo.SaveOrder;
import com.hangzhou.pojo.SaveOrderConvert;
import com.hangzhou.pojo.UpdateOrder;
import com.hangzhou.pojo.UpdateOrderVO;
import org.springframework.stereotype.Service;

/**
 * @Author Faye
 * @Date 2022/11/28 17:41
 */
@Service
public class OrderService {
    @OperationLog(desc = "保存订单", convert = SaveOrderConvert.class)
    public Boolean saveOrder(SaveOrder saveOrder) {
        System.out.println("save order,orderId:" + saveOrder.getId());
        return true;
    }

    @OperationLog(desc = "更新订单", convert = UpdateOrderVO.class)
    public Boolean updateOrder(UpdateOrder updateOrder) {
        System.out.println("update order,orderId:" + updateOrder.getOrderId());
        return true;
    }
}
