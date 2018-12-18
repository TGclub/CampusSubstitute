package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.AdminInfo;
import com.wizzstudio.substitute.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kikyou on 18-11-18
 */
@Repository
public interface AdminDao extends JpaRepository<AdminInfo, Integer> {
    AdminInfo getAdminInfoByAdminId(Integer id);

    AdminInfo getAdminInfoByAdminName(String name);

    List<AdminInfo> getAdminInfoByAdminRole(Role role);

    /**
     * 通过学校id 获取所有该学校的区域负责人，或非负责人
     */
    List<AdminInfo> findByAdminSchoolIdAndIsBoss(int schoolId, boolean isBoss);

    /**
     * 通过学校id 获取所有二级管理员
     */
    List<AdminInfo> findByAdminSchoolIdAndAdminRoleIs(int schoolId,Role role);

}
