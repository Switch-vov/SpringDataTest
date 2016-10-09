package com.netease.jdbc;

import com.netease.inter.ITransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * 账号Dao
 * Created by switch on 16/10/9.
 */
// 配置注解账号DaoBean
@Repository("jdbcAccountDao")
public class JDBCAccountDao implements ITransfer {
    private JdbcTemplate jdbcTemplate = null;

    // 自动注解
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 事务注解
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean transferMoney(Long srcUserId, Long targetUserId, double count) {
        // 转账成功标识
        boolean isOk = true;
        try {
            // 获取转账账号余额
            Long srcBalance = jdbcTemplate.queryForObject("SELECT balance FROM UserBalance where userId=?", Long.class, new Object[]{srcUserId});
            // 余额不足
            if (srcBalance < count) {
                isOk = false;
            }
            // 转账
            // 转账者余额减去count
            jdbcTemplate.update("UPDATE UserBalance SET balance=balance-? WHERE userId=?", new Object[]{count, srcUserId});
            // 模拟异常，测试事务是否开启
            throwException();
            // 被转账者余额加上count
            jdbcTemplate.update("UPDATE UserBalance SET balance=balance+? WHERE userId=?", new Object[]{count, targetUserId});
        } catch (Exception e) {
            // 抛出异常则认为转账失败
            isOk = false;
            e.printStackTrace();
        }
        return isOk;
    }

    private void throwException() {
        throw new RuntimeException();
    }
}
