package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.enums.UrgentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer>,IndentDaoCustom {

    Indent findByIndentId(Integer indentId);
    List<Indent> findByPerformerId(String userId);
    List<Indent> findByPublisherId(String publisherId);

    //广场订单列表查询
    //匹配所有收获地址符合要求的订单
    @Query("select i from Indent i where i.indentState = 'WAIT_FOR_PERFORMER' and i.shippingAddressId in ?1")
    List<Indent> findWaitByShippingAddressIdIn(List<Integer> shippingAddressIds);
    //匹配所有收获地址符合要求的订单，根据价格由低到高排序
    @Query("select i from Indent i where i.indentState = 'WAIT_FOR_PERFORMER' and i.shippingAddressId in ?1 " +
            "order by i.indentPrice desc")
    List<Indent> findWaitByShippingAddressIdInOrderByIndentPriceDesc(List<Integer> shippingAddressIds);
    //匹配所有收获地址符合要求的订单，根据时间由早到晚排序
    @Query("select i from Indent i where i.indentState = 'WAIT_FOR_PERFORMER' and i.shippingAddressId in ?1 " +
            "order by i.createTime desc")
    List<Indent> findWaitByShippingAddressIdInOrderByCreateTimeDesc(List<Integer> shippingAddressIds);

    /**
     * 根据业务逻辑，推荐页应只显示尚未被接的订单， 故以下三个方法全部find by indent state, 暂时没有收到分页请求故未做更多处理
     * @param state 订单状态
     * @return
     */
    List<Indent> findAllByIndentState(IndentStateEnum state);
    List<Indent> findAllByIndentStateOrderByIndentPriceDesc(IndentStateEnum state);
    List<Indent> findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum state);

    @Query(value = "select Indent from Indent i where i.isSolved = ?1 and i.urgentType > ?2")
    List<Indent> findAllByIsSolvedAndUrgentType(Boolean isSolved, Integer type);



}
