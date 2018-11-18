package com.wizzstudio.substitute.service;

import com.wizzstudio.substitute.dao.AddressDao;
import com.wizzstudio.substitute.dao.SchoolDao;
import com.wizzstudio.substitute.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class BaseService {

    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected UserDao userDao;

    @Autowired
    protected SchoolDao schoolDao;

    @Autowired
    protected AddressDao addressDao;

}
