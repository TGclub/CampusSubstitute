package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.Indent;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndentService {
    /**
     * 创建新的订单
     * @param indent
     */
    void publishedNewIndent(Indent indent, String clientIp);

    /**
     * 获取用户已发布的订单
     * @param userOpenid
     * @return
     */
    List<Indent> getUserPublishedIndent(String userOpenid);

    /**
     * 获取用户接受的订单
     * @param userId
     * @return
     */
    List<Indent> getUserPerformedIndent(String userId);

    /**
     * 获取指定订单信息
     * @param indentId
     * @return
     */
    Indent getSpecificIndentInfo(Integer indentId);

    /**
     * 增加赏金，每次只增加一元
     */
    void addIndentPrice(Integer indentId);

    /**
     *
     * @param type 排序类型
     * @param shippingAddress 送达地址
     * @return
     */
    Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress, Integer start);

    List<Indent> getIndentByCreateTime();

    List<Indent> getAllIndent();

    List<Indent> getIndentByPrice();


}
