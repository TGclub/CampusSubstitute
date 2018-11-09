package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.enums.IndentStateEnum;
import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer> {

    Indent findByIndentId(Integer indentId);
    List<Indent> findByPerformerId(String userId);
    List<Indent> findByPublisherOpenid(String userId);

    /**
     * 根据业务逻辑，推荐页应只显示尚未被接的订单， 故以下三个方法全部find by indent state, 暂时没有收到分页请求故未做更多处理
     * @param state 订单状态
     * @return
     */
    List<Indent> findAllByIndentState(IndentStateEnum state);
    List<Indent> findAllByIndentStateOrderByIndentPriceDesc(IndentStateEnum state);
    List<Indent> findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum state);



}
