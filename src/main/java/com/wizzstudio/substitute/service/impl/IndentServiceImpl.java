package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.pojo.Indent;
import com.wizzstudio.substitute.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao indentDao;

    @Override
    public void publishedNewIndent(Indent indent) {
        indentDao.save(indent);
    }

    @Override
    public Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress, Integer start) {

        return null;
    }

    @Override
    public List<Indent> getUserPublishedIndent(String userId) {
        return indentDao.findByPublisherId(userId);

    }

    @Override
    public List<Indent> getUserPerformedIndent(String userId) {
        return indentDao.findByPerformerId(userId);
    }

    @Override
    public Indent getSpecificIndentInfo(Integer indentId) {
        return indentDao.findByIndentId(indentId);
    }

    @Override
    public void addIndentPrice(Integer indentId) {
        Indent indent = indentDao.findByIndentId(indentId);
        indent.setIndentPrice(indent.getIndentPrice().add(new BigDecimal(1)));
    }
}
