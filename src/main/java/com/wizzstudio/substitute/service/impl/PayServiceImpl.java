package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.WithdrawRequestDao;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.domain.WithdrawRequest;
import com.wizzstudio.substitute.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/12/5 18:41
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    @Autowired
    WithdrawRequestDao withdrawRequestDao;
    @Autowired
    CommonCheckService commonCheckService;

    @Override
    public void createWithdraw(String userId) {
        commonCheckService.checkUserByUserId(userId);
        WithdrawRequest withdrawRequest = WithdrawRequest.builder().userId(userId).build();
        withdrawRequestDao.save(withdrawRequest);
    }
}
