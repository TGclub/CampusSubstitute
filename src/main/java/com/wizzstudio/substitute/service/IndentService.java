package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IndentService {
    /**
     * 创建新的订单
     * @param indent
     */
    void publishedNewIndent(Indent indent);

    /**
     * 获取用户已发布的订单
     * @param userId
     * @return
     */
    List<Indent> getUserPublishedIndent(String userId);

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

    void addIndentPrice(Integer indentId);

    /**
     *
     * @param type 排序类型
     * @param shippingAddress 送达地址
     * @return
     */
    Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress, Integer start);
}
