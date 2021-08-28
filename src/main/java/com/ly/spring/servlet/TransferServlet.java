package com.ly.spring.servlet;

import com.ly.spring.factory.BeanFactory;
import com.ly.spring.factory.ProxyFactory;
import com.ly.spring.service.TransferService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在servlet3.0以后，我们可以不用再web.xml里面配置servlet，
 * 只需要加上@WebServlet注解就可以修改该servlet的属性了。
 * web.xml可以配置的servlet属性，在@WebServlet中都可以配置。
 */
@WebServlet(name="transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    // 首先从BeanFactory获取到proxyFactory代理工厂的实例化对象
    private final static ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
    private final static TransferService transferService = (TransferService) proxyFactory.getJdkProxy(BeanFactory.getBean("transferService")) ;

    // 直接重写doGet方法
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        try {
            transferService.transfer(fromCardNo, toCardNo, money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
