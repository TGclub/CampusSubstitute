package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.Indent;

import java.util.List;

/**
 * Created By Cx On 2018/11/21 22:33
 */
public interface IndentDaoCustom {

    List<Indent> findWaitByShippingAddressIdInOrderByDefault(List<Integer> shippingAddressIds);
}
