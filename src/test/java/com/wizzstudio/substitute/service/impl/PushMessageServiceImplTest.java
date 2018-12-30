package com.wizzstudio.substitute.service.impl;

import com.aliyuncs.exceptions.ClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created By Cx On 2018/12/30 17:22
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PushMessageServiceImplTest {

    @Autowired
    PushMessageServiceImpl pushMessageService;
    @Autowired
    IndentServiceImpl indentService;

    @Test
    public void sendMsg() throws ClientException {
        indentService.takeIndent(101,"aRxxld");
    }
}