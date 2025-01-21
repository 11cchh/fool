package com.hangzhou.db.router;

/**
 * 数据源上下文
 *
 * @Author Faye
 * @Date 2023/12/25 10:33
 */
public class DBContextHolder {
    private static final ThreadLocal<String> DB_KEY = new ThreadLocal<String>();
    private static final ThreadLocal<String> TB_KEY = new ThreadLocal<String>();

    public static void setDBKey(String dbKeyIdx){
        DB_KEY.set(dbKeyIdx);
    }

    public static String getDBKey(){
        return DB_KEY.get();
    }

    public static void setTBKey(String tbKeyIdx){
        TB_KEY.set(tbKeyIdx);
    }

    public static String getTBKey(){
        return TB_KEY.get();
    }

    public static void clearDBKey(){
        DB_KEY.remove();
    }

    public static void clearTBKey(){
        TB_KEY.remove();
    }
}
