package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.CouponRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2018/11/20 0:19
 */
@Repository
public interface CouponRecordDao extends JpaRepository<CouponRecord, Integer> {
}
