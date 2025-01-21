package com.hangzhou.db.router;

/**
 * 数据源基础配置
 *
 * @Author Faye
 * @Date 2023/12/25 10:43
 */
public class DBRouterBase {
    private String tbIdx;

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }
}
