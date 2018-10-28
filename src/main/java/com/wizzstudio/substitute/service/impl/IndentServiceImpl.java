package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.pojo.Indent;
import com.wizzstudio.substitute.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao intentDao;
    @Override
    public void publishedNewIndent(Indent indent) {
        intentDao.save(indent);
    }
}
