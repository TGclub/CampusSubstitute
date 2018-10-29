package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.domain.Page;

public interface IndentService {
    /**
     * 创建新的订单
     * @param indent
     */
    void publishedNewIndent(Indent indent);

    /**
     *
     * @param type 排序类型
     * @param shippingAddress 送达地址
     * @return
     */
    Page<Indent> getIndentInFuzzyMatching(Integer type, String shippingAddress);
}
