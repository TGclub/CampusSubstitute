package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.CouponInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created By Cx On 2018/11/20 0:19
 */
@Repository
public interface CouponInfoDao extends JpaRepository<CouponInfo, Integer> {
    CouponInfo findByCouponId(Integer id);

    List<CouponInfo> findAllByCouponIdIsIn(List<Integer> couponIds);

    List<CouponInfo> findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc(Date date);
}
