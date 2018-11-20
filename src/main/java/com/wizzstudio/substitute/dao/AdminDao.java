package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.domain.AdminInfo;
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
}
