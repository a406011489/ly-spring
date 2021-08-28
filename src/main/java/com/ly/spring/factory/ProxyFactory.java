package com.ly.spring.factory;

import com.ly.spring.utils.TransactionManager;

import java.lang.reflect.Proxy;

/**
 * 代理对象工厂：生成代理对象的
 */
public class ProxyFactory {

    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Jdk动态代理
     *
     * @param obj 委托对象
     * @return 代理对象
     */
    public Object getJdkProxy(Object obj) {
        // 获取代理对象
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object result;
                    try {
                        // 开启事务(关闭事务的自动提交)
                        transactionManager.beginTransaction();
                        result = method.invoke(obj, args);
                        // 提交事务
                        transactionManager.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 回滚事务
                        transactionManager.rollback();
                        // 抛出异常便于上层servlet捕获
                        throw e;
                    }
                    return result;
                });
    }
}