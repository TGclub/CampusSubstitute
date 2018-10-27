package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    User findByOpenid(String id);
    User findUserById(String id);

}
