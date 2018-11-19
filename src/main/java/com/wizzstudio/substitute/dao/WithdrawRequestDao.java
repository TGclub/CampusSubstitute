package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.WithdrawRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2018/11/20 0:22
 */
@Repository
public interface WithdrawRequestDao extends JpaRepository<WithdrawRequest,Integer> {
}
