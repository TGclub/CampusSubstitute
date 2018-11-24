package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.DepositInfoDao;
import com.wizzstudio.substitute.domain.DepositInfo;
import com.wizzstudio.substitute.service.DepositInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2018/11/21 21:11
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DepositInfoServiceImpl implements DepositInfoService {

    @Autowired
    DepositInfoDao depositInfoDao;

    @Override
    public DepositInfo findById(String depositId) {
        return depositInfoDao.findById(depositId).orElse(null);
    }

    @Override
    public void save(DepositInfo depositInfo) {
        depositInfoDao.save(depositInfo);
    }


}
