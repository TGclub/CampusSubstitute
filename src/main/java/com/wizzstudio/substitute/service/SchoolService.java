package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.School;

/**
 * Created By Cx On 2018/11/27 13:16
 */
public interface SchoolService {

    /**
     * 通过id获得学校信息
     */
    School getById(Integer schoolId);
}
