package com.wizzstudio.substitute.dao;

import com.wizzstudio.substitute.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    User findByOpenid(String id);
    User findUserById(String id);

    @Query (value = "select user from  User user where user.masterId = :masterId")
    List<User> findByMasterId(@Param("masterId") String masterId);


}
