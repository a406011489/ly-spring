package com.ly.spring.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtils {

    // 存储当前线程的连接
    private final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 从当前线程获取连接
     */
    public Connection getCurrentThreadCon() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            // 从连接池拿连接并绑定到线程
            connection = HikariUtils.getInstance().getConnection();
            // 绑定到当前线程
            threadLocal.set(connection);
        }
        return connection;
    }
}
