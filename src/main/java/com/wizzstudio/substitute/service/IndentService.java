package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.Indent;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IndentService {
    /**
     * 创建新的订单
     * @param indent 订单信息
     */
    void save(Indent indent);

    /**
     * 获取用户已发布的订单
     * @param userOpenid
     * @return
     */
    List<Indent> getUserPublishedIndent(String userOpenid);

    /**
     * 获取用户接的订单
     * @param userId
     * @return
     */
    List<Indent> getUserPerformedIndent(String userId);

    /**
     * 获取指定订单信息
     * @param indentId
     * @return
     */
    Indent getIndentDetail(Integer indentId, String userId);

    /**
     * 增加赏金，每次只增加一元
     */
    void addIndentPrice(Integer indentId,String userId);

    /**
     * 通过shippingAddress模糊匹配查询订单列表，排序方式为sortType
     * 默认：0，时间：10，价格:20
     * @param sortType 排序类型
     * @param shippingAddress 送达地址(模糊匹配)
     * @return
     */
    List<Indent> getWaitInFuzzyMatching(Integer sortType, String shippingAddress);

    List<Indent> getIndentByCreateTime();

    List<Indent> getAllIndent();

    List<Indent> getIndentByPrice();


}
