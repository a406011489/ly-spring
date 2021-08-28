package com.ly.spring.dao;


import com.ly.spring.entity.Account;
import com.ly.spring.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcAccountDaoImpl implements AccountDao {

    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }


    public void init() {
        System.out.println("初始化方法.....");
    }

    public void destory() {
        System.out.println("销毁方法......");
    }

    @Override
    public Account queryAccountByCardNo(String account) throws Exception {

        // 从当前线程获取连接
        Connection con = connectionUtils.getCurrentThreadCon();

        String sql = "select * from account where account=?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1,account);

        ResultSet resultSet = preparedStatement.executeQuery();

        Account account1 = new Account();
        while(resultSet.next()) {
            account1.setAccount(resultSet.getString("account"));
            account1.setName(resultSet.getString("name"));
            account1.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
        //con.close();

        return account1;
    }

    @Override
    public void updateAccountByCardNo(Account account) throws Exception {

        // 从当前线程获取数据库连接
        Connection con = connectionUtils.getCurrentThreadCon();

        String sql = "update account set money=? where account=?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getAccount());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        //con.close();
    }
}
