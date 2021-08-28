package com.ly.spring.service;


import com.ly.spring.dao.AccountDao;
import com.ly.spring.entity.Account;
import com.ly.spring.entity.Result;
import com.ly.spring.utils.TransactionManager;

public class TransferServiceImpl implements TransferService {

    // 最佳状态
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        accountDao.updateAccountByCardNo(to);
        int a = 1 /0;
        accountDao.updateAccountByCardNo(from);
    }
}
