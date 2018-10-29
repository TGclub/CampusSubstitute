package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.Indent;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndentDao extends JpaRepository<Indent, Integer> {

    Page<Indent> findByShippingAddressLikeOrderByPriceDesc(String shippingAddress);
    Page<Indent> findByShippingAddressLikeOrderByCreateTimeDesc(String shippingAddress);
    Page<Indent> findByShippingAddressLike(String shippingAddress);
}
