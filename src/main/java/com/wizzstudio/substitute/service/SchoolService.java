package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.domain.School;

import java.util.List;

/**
 * Created By Cx On 2018/11/27 13:16
 */
public interface SchoolService {

    /**
     * 通过id获得学校信息
     */
    School getById(Integer schoolId);


    /**
     * 通过关键字获取学校
     *
     * @param school 学校名称
     * @return
     */
    List<School> getSchoolInFuzzyMatching(String school);

    /**
     * 插入学校信息
     */
    void save(School school);

}
