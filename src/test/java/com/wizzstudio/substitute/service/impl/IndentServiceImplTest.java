package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.domain.Indent;
import com.wizzstudio.substitute.service.IndentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;

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

    @Test
    public void getUserPublishedIndent() {
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