package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.dto.IndentWxPrePayDto;
import com.wizzstudio.substitute.enums.IndentStateEnum;
import com.wizzstudio.substitute.pojo.Indent;
import com.wizzstudio.substitute.service.IndentService;
import com.wizzstudio.substitute.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
//todo 加上事务，在controller层调用会报错
@Transactional(rollbackFor = Exception.class)
public class IndentServiceImpl implements IndentService {

    @Autowired
    IndentDao indentDao;
    @Autowired
    PayService payService;

    private IndentWxPrePayDto createIndentWxPrePayDto(Indent indent, String clientIp) {
        int totalFee = indent.getIndentPrice().multiply(new BigDecimal(100)).intValue();
        return IndentWxPrePayDto.builder().indentId(indent.getIndentId().toString())
                .openid(indent.getPublisherOpenid())
                .totalFee(totalFee)
                .clientIp(clientIp)
                .build();
    }

    /**
     * 创建新订单
     * clientIp为客户端IP地址，支付时需要
     */
    @Override
    public void publishedNewIndent(Indent indent, String clientIp) {
        //设置订单状态
        indent.setIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
        //将订单保存到数据库中
        indent = indentDao.save(indent);
        //调用预支付接口，并返回给前端支付参数
        payService.prePay(createIndentWxPrePayDto(indent, clientIp));
    }

    @Override
    public Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress, Integer start) {
        System.out.println("hello");
        return null;
    }

    @Override
    public List<Indent> getUserPublishedIndent(String userOpenid) {
        return indentDao.findByPublisherOpenid(userOpenid);

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

    @Override
    public List<Indent> getAllIndent() {
        return indentDao.findAllByIndentState(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

    @Override
    public List<Indent> getIndentByPrice() {
        return indentDao.findAllByIndentStateOrderByIndentPriceDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

    @Override
    public List<Indent> getIndentByCreateTime() {
        return indentDao.findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum.WAIT_FOR_PERFORMER);
    }

}
