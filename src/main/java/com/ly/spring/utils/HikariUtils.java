package com.ly.spring.utils;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 本案例采用光连接池
 */
public class HikariUtils {

    private HikariUtils(){
    }

    private final static HikariDataSource hikariDataSource = new HikariDataSource();

    // 那程序启动时，会自动执行static代码块，把光连接池注入进去
    static {
        hikariDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=GMT%2B8");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("begin123");
    }

    public static HikariDataSource getInstance() {
        return hikariDataSource;
    }
}
