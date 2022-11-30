package com.hangzhou.pojo;

import com.hangzhou.aop.Convert;

/**
 * @Author Faye
 * @Date 2022/11/29 17:35
 */
public class UpdateOrderVO implements Convert<UpdateOrder> {
    @Override
    public OperationLogVO convert(UpdateOrder updateOrder) {
        OperationLogVO operationLogVO = new OperationLogVO();
        operationLogVO.setOrderId(updateOrder.getOrderId());
        return operationLogVO;
    }
}
