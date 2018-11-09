package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer> {

    Indent findByIndentId(Integer indentId);
    List<Indent> findByPerformerId(String userId);
    List<Indent> findByPublisherOpenid(String userId);

}
