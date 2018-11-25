package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.DepositInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2018/11/21 21:10
 */
@Repository
public interface DepositInfoDao extends JpaRepository<DepositInfo, String> {
}
