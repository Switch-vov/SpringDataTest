package com.netease.inter;

/**
 * 转账接口
 * Created by switch on 16/10/9.
 */
public interface ITransfer {
    /**
     * 转钱
     *
     * @param srcUserId    转钱者
     * @param targetUserId 收钱者
     * @param count        钱的数额
     * @return 是否成功
     */
    public boolean transferMoney(Long srcUserId, Long targetUserId, double count);
}
