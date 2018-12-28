package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.constants.Constant;
import com.wizzstudio.substitute.dao.WithdrawRequestDao;
import com.wizzstudio.substitute.domain.User;
import com.wizzstudio.substitute.domain.WithdrawRequest;
import com.wizzstudio.substitute.exception.SubstituteException;
import com.wizzstudio.substitute.service.PayService;
import com.wizzstudio.substitute.util.RedisUtil;
import com.wizzstudio.substitute.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void createWithdraw(String userId) {
        if (redisUtil.get(Constant.WITHDRAW.concat(userId)) != null) {
            log.error("[提现]提现失败，一天只能提现一次，userId={}", userId);
            throw new SubstituteException("一天只能提现一次");
        } else {
            redisUtil.store(Constant.WITHDRAW.concat(userId), "true",
                    Math.toIntExact(TimeUtil.getLastTime().getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        }
        commonCheckService.checkUserByUserId(userId);
        WithdrawRequest withdrawRequest = WithdrawRequest.builder().userId(userId).build();
        withdrawRequestDao.save(withdrawRequest);
    }
}
