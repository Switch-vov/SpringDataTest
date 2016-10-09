package com.netease.mybatis;

import com.netease.inter.ITransfer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by switch on 16/10/9.
 */
public interface MyBatisAccountDao extends ITransfer {

    @Transactional(propagation = Propagation.REQUIRED)
    @Update("UPDATE UserBalance SET balance=balance-#{count} WHERE userId=#{srcUserId};UPDATE UserBalance SET balance=balance+#{count} WHERE userId=#{targetUserId}")
    public boolean transferMoney(@Param("srcUserId") Long srcUserId, @Param("targetUserId") Long targetUserId, @Param("count") double count);

}
