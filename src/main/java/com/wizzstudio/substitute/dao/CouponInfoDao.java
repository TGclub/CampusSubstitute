package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.CouponInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created By Cx On 2018/11/20 0:19
 */
@Repository
public interface CouponInfoDao extends JpaRepository<CouponInfo, Integer> {
    CouponInfo findByCouponId(Integer id);

    /**
     * 通过couponId列表，获取所有优惠券信息
     */
    List<CouponInfo> findAllByCouponIdIsIn(List<Integer> couponIds);

    /**
     * 获取所有可领取的优惠券
     */
    @Query("select c from CouponInfo c where c.invalidTime > current_date ")
    List<CouponInfo> findAllLive();

    List<CouponInfo> findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc(Date date);
}
