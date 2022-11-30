package com.hangzhou;

import com.hangzhou.pojo.SaveOrder;
import com.hangzhou.pojo.UpdateOrder;
import com.hangzhou.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 操作日志记录
 *
 * @Author Faye
 * @Date 2022/11/28
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class LogRecordApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogRecordApplication.class, args);
    }
}