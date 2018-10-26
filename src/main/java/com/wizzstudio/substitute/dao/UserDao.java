package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByOpenid(String id);

}
