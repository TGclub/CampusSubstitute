package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer> {
}
