package com.hangzhou.pojo;

import com.hangzhou.aop.Convert;

/**
 * @Author Faye
 * @Date 2022/11/29 17:33
 */
public class SaveOrderConvert implements Convert<SaveOrder> {
    @Override
    public OperationLogVO convert(SaveOrder saveOrder) {
        OperationLogVO operationLogVO = new OperationLogVO();
        operationLogVO.setOrderId(saveOrder.getId());
        return operationLogVO;
    }
}
