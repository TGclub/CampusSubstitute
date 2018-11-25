package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.TradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2018/11/20 0:21
 */
@Repository
public interface TradeInfoDao extends JpaRepository<TradeInfo, Integer> {
}
