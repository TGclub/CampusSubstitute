package com.wizzstudio.substitute.config;

import com.wizzstudio.substitute.domain.Config;

import java.math.BigDecimal;

/**
 * @author kikyou
 */
public class AdminConfigurableConfig {

    public static BigDecimal companyRatio;

    //推荐人分成占比
    public static BigDecimal masterRatio;

    //接单人分成占比
    public static BigDecimal performerRatio;

    //最低单价
    public static Integer leastPrice;

    //超时时间，单位：s
    public static Integer overTime;

    public static void setConfig(Config config) {
        AdminConfigurableConfig.companyRatio = config.getCompanyRatio();
        AdminConfigurableConfig.masterRatio = config.getMasterRatio();
        AdminConfigurableConfig.performerRatio = config.getPerformerRatio();
        AdminConfigurableConfig.leastPrice = config.getLeastPrice();
        AdminConfigurableConfig.overTime = config.getOverTime();
    }
}
