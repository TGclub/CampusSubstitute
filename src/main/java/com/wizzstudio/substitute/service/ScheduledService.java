package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.enums.CountInfoTypeEnum;

/**
 * Created by Kikyou on 18-12-2
 */
public interface ScheduledService {
    /**
     * 检查超时订单
     */
    void checkOutOfTimeIndent();

    /**
     * 更新每日的统计信息
     */
    void saveEveryDaysCount();

    /**
     * 统计量+1
     * @param schoolId
     * @param type
     */
    void update(Integer schoolId, CountInfoTypeEnum type, Object value);
}
