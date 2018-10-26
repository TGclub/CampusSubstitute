package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class BaseService {

    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected UserDao userDao;
}
