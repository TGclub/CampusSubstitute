package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.pojo.Indent;
import com.wizzstudio.substitute.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao indentDao;

    @Override
    public void publishedNewIndent(Indent indent) {
        indentDao.save(indent);
    }

    @Override
    public Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress) {
        Page<Indent> indents;
        shippingAddress = "%" + shippingAddress + "%";
        switch (type) {
            case 10:
                indents = indentDao.findByShippingAddressLikeOrderByCreateTimeDesc(shippingAddress);
            case 20:
                indents = indentDao.findByShippingAddressLikeOrderByPriceDesc(shippingAddress);
            default:
                indents = indentDao.findByShippingAddressLike(shippingAddress);
        }
        return indents;
    }
}
