package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.SchoolDao;
import com.wizzstudio.substitute.domain.School;
import com.wizzstudio.substitute.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By Cx On 2018/11/27 13:16
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolDao schoolDao;

    @Override
    public School getById(Integer schoolId) {
        return schoolDao.findSchoolById(schoolId);
    }

    /**
     * 采取简单粗暴的模糊匹配
     *
     * @param school 学校名称
     * @return
     */
    @Override
    public List<School> getSchoolInFuzzyMatching(String school) {
        return schoolDao.findBySchoolNameLike("%" + school + "%");
    }

    @Override
    public void save(School school) {
        schoolDao.save(school);
    }

}
