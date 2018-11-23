package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.DepositInfo;

/**
 * 充值信息有关业务
 * Created By Cx On 2018/11/21 21:11
 */
public interface DepositInfoService {
    /**
     * 通过id获得实体
     */
    DepositInfo findById(String depositId);

    /**
     * 插入新实体
     */
    void save(DepositInfo depositInfo);
}
