package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dao.IndentDao;
import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.service.IndentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;


import static com.wizzstudio.substitute.enums.GenderEnum.NO_LIMITED;
import static com.wizzstudio.substitute.enums.indent.IndentTypeEnum.HELP_OTHER;

/**
 * Created By Cx On 2018/11/8 14:56
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class IndentServiceImplTest {

    @Autowired
    IndentService indentService;
    @Autowired
    IndentDao indentDao;
    @Autowired
    IndentServiceImpl indentServiceImpl;

    @Test
    public void publishedNewIndent() {
        Indent indent = new Indent();
        indent.setIndentType(HELP_OTHER);
        indent.setRequireGender(NO_LIMITED);
        indent.setPublisherId("TEST");
        indent.setIndentContent("hello");
        indent.setIndentPrice(1);
        indentService.create(indent);
    }

    @Test
    public void getIndentInFuzzyMatching() {
    }

    @Rollback(false)
    @Test
    public void Indent2VO() {
        //todo 如果secretText不是null会在执行完(不是下面这条语句，而是所有语句执行完，即该方法执行完)以后update为null
//        System.out.println(indentService.getIndentDetail(9,"8"));
        indentServiceImpl.test();
        System.out.println("what???");
        System.out.println("xxxxxxx");
    }

    @Test
    public void getUserPerformedIndent() {
    }

    @Test
    public void getSpecificIndentInfo() {
    }

    @Test
    public void addIndentPrice() {
    }
}