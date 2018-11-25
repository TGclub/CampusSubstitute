package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.CouponInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By Cx On 2018/11/20 0:19
 */
@Repository
public interface CouponInfoDao extends JpaRepository<CouponInfo, Integer> {
    CouponInfo findByCouponId(Integer id);
}
