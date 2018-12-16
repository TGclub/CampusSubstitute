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
    List<CouponInfo> findAllByCouponIdIsInAndIsDeletedIsFalse(List<Integer> couponIds);

    /**
     * 获取除指定的可领取的优惠券
     * @param couponId 该集合为已领取的优惠券id集合
     */
    @Query("select c from CouponInfo c where c.invalidTime > current_date and c.isDeleted = false and c.couponId not in ?1")
    List<CouponInfo> findAllGetExcept(List<Integer> couponId);

    /**
     * 获取所有可领取的优惠券
     */
    @Query("select c from CouponInfo c where c.invalidTime > current_date and c.isDeleted = false")
    List<CouponInfo> findAllGet();

    List<CouponInfo> findTop5ByInvalidTimeAfterAndIsDeletedIsFalseOrderByCouponIdDesc(Date date);
}
