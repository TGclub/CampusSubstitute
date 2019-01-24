package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.ConfigDao;
import com.wizzstudio.substitute.domain.Config;
import com.wizzstudio.substitute.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created By Cx On 2019/1/24 9:55
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigDao configDao;

    @Override
    public Config findById(Integer id) {
        return configDao.findById(id).orElse(null);
    }
}
