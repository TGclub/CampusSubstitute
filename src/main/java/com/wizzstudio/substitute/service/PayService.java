package com.wizzstudio.substitute.service;


/**
 * 除微信充值以为和金钱交易有关service
 * Created By Cx On 2018/12/5 18:41
 */
public interface PayService {
    /**
     * 创建某用户的提现请求
     */
    void createWithdraw(String userId);
}
