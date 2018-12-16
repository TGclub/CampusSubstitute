package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.CouponRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Cx On 2018/11/20 0:19
 */
@Repository
public interface CouponRecordDao extends JpaRepository<CouponRecord, Integer> {

    /**
     * 通过用户id和优惠券id，查询某用户是否有领取该优惠券的记录
     */
    CouponRecord findByCouponIdAndOwnerId(Integer couponId,String ownerId);

    /**
     * 获取指定用户已领取的所有优惠券
     */
    @Query("select c from CouponRecord c where c.ownerId = ?1 and c.invalidTime > current_date ")
    List<CouponRecord> findGetByUserId(String userId);

    /**
     * 获取指定用户已领取的未过期优惠券
     */
    @Query("select c from CouponRecord c where c.isUsed = false and c.ownerId = ?1 and c.invalidTime > current_date ")
    List<CouponRecord> findLiveByUserId(String userId);
}
