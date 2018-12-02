package com.wizzstudio.substitute.enums.indent;

/**
 * 订单状态
 */
public enum IndentStateEnum {
    /**
     * 等待接单
     */
    WAIT_FOR_PERFORMER,
    /**
     * 执行中
     */
    PERFORMING,
    /**
     * 货物已送达
     */
    ARRIVED,
    /**
     * 订单完成
     */
    COMPLETED,
    /**
     * 订单取消，仅可在订单完成前取消订单
     */
    CANCELED,
    /**
     * 订单取消
     */
    OUT_OF_TIME
}
