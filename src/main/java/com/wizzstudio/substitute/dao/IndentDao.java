package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.enums.GenderEnum;
import com.wizzstudio.substitute.enums.indent.IndentStateEnum;
import com.wizzstudio.substitute.domain.Indent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer> {

    Indent findByIndentId(Integer indentId);

    List<Indent> findByPerformerId(String userId);

    List<Indent> findByPublisherId(String publisherId);

    /**
     * 广场订单列表查询
     * 根据业务逻辑，推荐页应只显示尚未被接的订单, 暂时没有收到分页请求故未做更多处理
     *
     * @param state 订单状态
     */
    List<Indent> findAllByIndentStateAndRequireGenderNot(IndentStateEnum state, GenderEnum excludeGender);

    List<Indent> findAllByIndentStateAndRequireGenderNotOrderByIndentPriceDesc(IndentStateEnum state, GenderEnum excludeGender);

    List<Indent> findAllByIndentStateAndRequireGenderNotOrderByCreateTimeDesc(IndentStateEnum state, GenderEnum excludeGender);

    List<Indent> findAllByIndentStateOrderByCreateTimeDesc(IndentStateEnum state);

    //@Query(value = "select i from Indent i where i.isSolved = ?1 and i.urgentType > ?2")
    List<Indent> findAllByIsSolvedAndUrgentTypeGreaterThanOrderByCreateTimeDesc(Boolean isSolved, Integer type);


}
