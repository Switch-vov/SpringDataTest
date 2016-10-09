package com.netease;

import com.netease.jdbc.JDBCAccountDao;
import com.netease.mybatis.MyBatisAccountDao;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.annotations.Param;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 单元测试
 * Created by switch on 16/10/9.
 */
public class TestData {
    private ApplicationContext context = null;
    private JDBCAccountDao jdbcAccountDao = null;
    private MyBatisAccountDao myBatisAccountDao = null;

    @Before
    public void init() {
        String resource = "application-context.xml";
        context = new ClassPathXmlApplicationContext(resource);
        jdbcAccountDao = context.getBean("jdbcAccountDao",JDBCAccountDao.class);
        myBatisAccountDao = context.getBean("myBatisAccountDao", MyBatisAccountDao.class);
    }

    @Test
    public void test_data_source() {
        BasicDataSource dataSource = (BasicDataSource) context.getBean("dataSource");
        Connection connection = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            pstat = connection.prepareStatement("select * from UserBalance");
            rs = pstat.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("userName") + " " + rs.getString("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_jdbc_account_dao_transfer_money() {
        boolean b = jdbcAccountDao.transferMoney(10000L, 10001L, 1000);
        handlerMessage(b);
    }

    private void handlerMessage(boolean b) {
        if(b) {
            System.out.println("转账成功");
        } else {
            System.out.println("转账失败");
        }
    }

    @Test
    public void test_mybatis_account_dao_transfer_money() {
        boolean b = myBatisAccountDao.transferMoney(10000L, 10001L, 1000);
        handlerMessage(b);
    }

    @After
    public void destory() {
        ((ConfigurableApplicationContext)context).close();
    }

}
