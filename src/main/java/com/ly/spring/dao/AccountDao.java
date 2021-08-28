package com.ly.spring.dao;


import com.ly.spring.entity.Account;

public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    void updateAccountByCardNo(Account account) throws Exception;
}
