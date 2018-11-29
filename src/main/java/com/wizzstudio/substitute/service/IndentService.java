package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.VO.IndentVO;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.enums.GenderEnum;

import java.util.List;

public interface IndentService {
    /**
     * 创建新的订单
     * 若用户余额不足，则抛异常
     * @param indent 订单信息
     */
    void create(Indent indent);

    /**
     * 增加赏金，每次只增加一元
     * 若用户余额不足，则抛异常
     */
    void addIndentPrice(Integer indentId, String userId);

    /**
     * 用户接单接口
     */
    void takeIndent(Integer indentId, String userId);

    /**
     * 接单人送达接口
     */
    void arrivedIndent(Integer indentId, String userId);

    /**
     * 完结订单
     */
    void finishedIndent(Integer indentId, String userId);

    /**
     * 取消订单
     */
    void canceledIndent(Integer indentId, String userId);

    /**
     * 获取用户已发布的订单
     *
     * @param userId 用户id
     * @return 该用户已发布的订单列表
     */
    List<IndentVO> getUserPublishedIndent(String userId);

    /**
     * 获取用户接的订单
     *
     * @param userId 用户id
     * @return 该用户已接订单列表
     */
    List<IndentVO> getUserPerformedIndent(String userId);

    /**
     * 查询同性别的订单列表，排序方式为sortType
     * 默认：0，时间：10，价格:20
     * @param sortType        排序类型
     * @param sexType         用户性别
     * @return 订单列表
     */
    List<IndentVO> getWait(Integer sortType, GenderEnum sexType);

    /**
     * 获取指定订单信息
     *
     * @param indentId 订单id
     * @return 订单信息
     */
    IndentVO getIndentDetail(Integer indentId, String userId);
}
