package com.wizzstudio.substitute.service.impl;

import com.wizzstudio.substitute.dto.WxPayInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * Created By Cx On 2018/11/6 20:48
 */
@SpringBootTest
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class PayServiceImplTest {

    @Autowired
    PayServiceImpl payService;

    @Test
    public void getSign() {
        WxPayInfo wxPayInfo = new WxPayInfo();
        wxPayInfo.setAppid("wxd930ea5d5a258f4f");
        wxPayInfo.setBody("test");
        wxPayInfo.setDevice_info("1000");
        wxPayInfo.setMch_id("10000100");
        wxPayInfo.setNonce_str("ibuaiVcKdpRxkhJA");
        System.out.println("hello world!");
    }
}